package com.sct.rest.api.service;

import com.sct.rest.api.mapper.rent.RentMapper;
import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripInputBeginDto;
import com.sct.rest.api.model.dto.trip.TripInputEndDto;
import com.project.model.entity.*;
import com.sct.rest.api.model.entity.*;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.repository.UserRepository;
import com.sct.rest.api.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource(value = "classpath:/price.yml")
public class TripService {
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final TransportRepository transportRepository;
    private final RentRepository rentRepository;
    private final RentMapper rentMapper;

    @Value("${initialPriceForBicycle}")
    private BigDecimal initialPriceForBicycle;
    @Value("${initialPriceForScooter}")
    private BigDecimal initialPriceForScooter;
    @Value("${pricePerMinuteForBicycle}")
    private BigDecimal pricePerMinuteForBicycle;
    @Value("${pricePerMinuteForScooter}")
    private BigDecimal pricePerMinuteForScooter;

    @Autowired
    public TripService(UserRepository userRepository, ParkingRepository parkingRepository, TransportRepository transportRepository, RentRepository rentRepository, RentMapper rentMapper){
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.transportRepository = transportRepository;
        this.rentRepository = rentRepository;
        this.rentMapper = rentMapper;
    }

    public List<RentDto> getAllRent(){
        Iterable<Rent> rentIterable = rentRepository.findAll();
        List<RentDto> rentDtoList = new ArrayList<>();
        for(var rent : rentIterable){
            rentDtoList.add(rentMapper.modelToDto(rent));
        }
        return rentDtoList;
    }

    public RentDto beginRent(TripInputBeginDto tripInputBeginDto){
        Optional<User> userOptional = userRepository.findById(SecurityContext.get().getUserId());
        Optional<Parking> parkingOptional = parkingRepository.findById(tripInputBeginDto.getParkingId());
        Optional<Transport> transportOptional = transportRepository.findById(tripInputBeginDto.getTransportId());

        User user = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), SecurityContext.get().getUserId()));
        Parking parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), tripInputBeginDto.getParkingId()));
        Transport transport = transportOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable(), tripInputBeginDto.getTransportId()));

        BigDecimal initialPrice = transport.getType() == TransportType.BICYCLE ? initialPriceForBicycle : initialPriceForScooter;

        if(transport.getParking() != null && transport.getStatus() == TransportStatus.FREE)
        {
            if(user.getBalance().intValue() < initialPrice.longValue() || transport.getStatus() != TransportStatus.FREE || transport.getParking().getId().intValue() != parking.getId().intValue()){
                throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
            }
        }
        else{
            throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
        }
        if(transport.getType() == TransportType.SCOOTER){
            if(transport.getChargePercentage() < 10){
                throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
            }
        }

        transport.setParking(null);
        transport.setCoordinates(null);
        transport.setStatus(TransportStatus.BUSY);
        user.setBalance(user.getBalance() - initialPrice.longValue());

        Rent rent = new Rent();
        rent.setUser(user);
        rent.setTransport(transport);
        rent.setBeginTimeRent(new Timestamp(System.currentTimeMillis()));
        rent.setBeginParking(parking);
        rent.setStatus(RentStatus.OPEN);

        userRepository.save(user);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);

        return rentMapper.modelToDto(rent);
    }

    public void endRent(TripInputEndDto tripInputEndDto){
        Optional<User> userOptional = userRepository.findById(SecurityContext.get().getUserId());
        Optional<Parking> parkingOptional = parkingRepository.findById(tripInputEndDto.getParkingId());
        Optional<Transport> transportOptional = transportRepository.findById(tripInputEndDto.getTransportId());
        Optional<Rent> rentOptional = rentRepository.findById(tripInputEndDto.getRentId());

        User user = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), SecurityContext.get().getUserId()));
        Parking parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), tripInputEndDto.getParkingId()));
        Transport transport = transportOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable(), tripInputEndDto.getTransportId()));
        Rent rent = rentOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.RENT_NOT_FOUND, new Throwable(), tripInputEndDto.getRentId()));

        BigDecimal initialPrice = transport.getType() == TransportType.BICYCLE ? initialPriceForBicycle : initialPriceForScooter;

        if(rent.getStatus() == RentStatus.CLOSE){
            throw new ServiceRuntimeException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, new Throwable());
        }

        Timestamp endTimeRent = Timestamp.valueOf(LocalDateTime.now());
        long minutes = (endTimeRent.getTime() - rent.getBeginTimeRent().getTime()) / 60000 + 1;

        long pricePerMinute;
        if(transport.getType() == TransportType.BICYCLE){
            pricePerMinute = pricePerMinuteForBicycle.longValue();
        }
        else{
            pricePerMinute = pricePerMinuteForScooter.longValue();
        }

        if(user.getBalance().intValue() < minutes * pricePerMinute){
            throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
        }

        long amount = minutes * pricePerMinute;
        user.setBalance(user.getBalance() - amount);

        if(transport.getType() == TransportType.SCOOTER){
            transport.setChargePercentage(transport.getChargePercentage() - 10);
        }

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        transport.setStatus(TransportStatus.FREE);

        rent.setEndTimeRent(endTimeRent);
        rent.setEndParking(parking);
        rent.setStatus(RentStatus.CLOSE);
        rent.setAmount(amount + initialPrice.longValue());

        userRepository.save(user);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);
    }

    public Long countRentByUserId(Long id){
        return rentRepository.countRentByUserId(id);
    }

    public List<RentDto> allRentByUser(String login){
        Iterable<Rent> rentIterable = rentRepository.allRentByUserLogin(login);
        List<RentDto> rentDtoList = new ArrayList<>();
        for(var rent : rentIterable){
            rentDtoList.add(rentMapper.modelToDto(rent));
        }
        return rentDtoList;
    }

    public RentDto beginRentBot(String userLogin ,String parkingName, String transportName){
        User user = userRepository.findByLogin(userLogin);
        Parking parking = parkingRepository.findByName(parkingName);
        Transport transport = transportRepository.findByIdentificationNumber(transportName);

        BigDecimal initialPrice = transport.getType() == TransportType.BICYCLE ? initialPriceForBicycle : initialPriceForScooter;

        if(transport.getParking() != null && transport.getStatus() == TransportStatus.FREE)
        {
            if(user.getBalance().intValue() < initialPrice.longValue() || transport.getStatus() != TransportStatus.FREE || transport.getParking().getId().intValue() != parking.getId().intValue()){
                throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
            }
        }
        else{
            throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
        }
        if(transport.getType() == TransportType.SCOOTER){
            if(transport.getChargePercentage() < 10){
                throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
            }
        }

        transport.setParking(null);
        transport.setCoordinates(null);
        transport.setStatus(TransportStatus.BUSY);
        user.setBalance(user.getBalance() - initialPrice.longValue());

        Rent rent = new Rent();
        rent.setUser(user);
        rent.setTransport(transport);
        rent.setBeginTimeRent(new Timestamp(System.currentTimeMillis()));
        rent.setBeginParking(parking);
        rent.setStatus(RentStatus.OPEN);

        userRepository.save(user);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);

        return rentMapper.modelToDto(rent);
    }

    public RentDto endRentBot(String userLogin ,String parkingName, String transportName){
        User user = userRepository.findByLogin(userLogin);
        Parking parking = parkingRepository.findByName(parkingName);
        Transport transport = transportRepository.findByIdentificationNumber(transportName);
        Rent rent = rentRepository.getRentByLoginAndTransport(userLogin, transportName);

        Timestamp endTimeRent = Timestamp.valueOf(LocalDateTime.now());
        long minutes = (endTimeRent.getTime() - rent.getBeginTimeRent().getTime()) / 60000 + 1;

        BigDecimal initialPrice = transport.getType() == TransportType.BICYCLE ? initialPriceForBicycle : initialPriceForScooter;

        long pricePerMinute;
        if(transport.getType() == TransportType.BICYCLE){
            pricePerMinute = pricePerMinuteForBicycle.longValue();
        }
        else{
            pricePerMinute = pricePerMinuteForScooter.longValue();
        }

        if(user.getBalance().intValue() < minutes * pricePerMinute){
            throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
        }

        long amount = minutes * pricePerMinute;
        user.setBalance(user.getBalance() - amount);

        if(transport.getType() == TransportType.SCOOTER){
            transport.setChargePercentage(transport.getChargePercentage() - 10);
        }

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        transport.setStatus(TransportStatus.FREE);

        rent.setEndTimeRent(endTimeRent);
        rent.setEndParking(parking);
        rent.setStatus(RentStatus.CLOSE);
        rent.setAmount(amount + initialPrice.longValue());

        userRepository.save(user);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);

        return rentMapper.modelToDto(rent);
    }
}