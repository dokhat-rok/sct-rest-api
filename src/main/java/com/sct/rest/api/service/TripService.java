package com.sct.rest.api.service;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.rent.RentMapper;
import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripBeginDto;
import com.sct.rest.api.model.dto.trip.TripEndDto;
import com.sct.rest.api.model.entity.Customer;
import com.sct.rest.api.model.entity.Parking;
import com.sct.rest.api.model.entity.Rent;
import com.sct.rest.api.model.entity.Transport;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.security.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TripService {

    private final CustomerRepository customerRepository;

    private final ParkingRepository parkingRepository;

    private final TransportRepository transportRepository;

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    private final PriceService priceService;

    public RentDto beginRent(TripBeginDto tripBegin) {
        Customer customer = this.getCustomer();
        Parking parking = this.getParking(tripBegin.getParkingId());
        Transport transport = this.getTransport(tripBegin.getTransportId());
        PriceDto price = priceService.getActualPrice(transport.getType());

        if (transport.getParking() != null && transport.getStatus() == TransportStatus.FREE) {
            if (customer.getBalance().intValue() < price.getInit() ||
                    transport.getStatus() != TransportStatus.FREE ||
                    transport.getParking().getId().intValue() != parking.getId().intValue()) {
                throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
            }
        } else {
            throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
        }

        if (transport.getType() == TransportType.SCOOTER) {
            if (transport.getChargePercentage() < 10) {
                throw new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_AVAILABLE, new Throwable());
            }
        }

        transport.setParking(null);
        transport.setCoordinates(null);
        transport.setStatus(TransportStatus.BUSY);
        customer.setBalance(customer.getBalance() - price.getInit());

        Rent rent = Rent.builder()
                .customer(customer)
                .transport(transport)
                .beginTimeRent(ZonedDateTime.now())
                .beginParking(parking)
                .status(RentStatus.OPEN)
                .build();

        customerRepository.save(customer);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);

        return rentMapper.modelToDto(rent);
    }

    public RentDto endRent(TripEndDto tripEnd) {
        Customer customer = this.getCustomer();
        Parking parking = this.getParking(tripEnd.getParkingId());
        Transport transport = this.getTransport(tripEnd.getTransportId());
        Rent rent = this.getRent(tripEnd.getRentId());
        PriceDto price = priceService.getActualPrice(transport.getType());

        if (rent.getStatus() == RentStatus.CLOSE)
            throw new ServiceRuntimeException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, new Throwable());

        ZonedDateTime endTimeRent = ZonedDateTime.now();
        long minutes = Duration.between(rent.getBeginTimeRent(), endTimeRent).toMinutes();
        long amount = minutes * price.getPerMinute();
        if (customer.getBalance().intValue() < amount)
            throw new ServiceRuntimeException(ErrorCodeEnum.NO_MONEY, new Throwable());
        customer.setBalance(customer.getBalance() - amount);

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        transport.setStatus(TransportStatus.FREE);

        rent.setEndTimeRent(endTimeRent);
        rent.setEndParking(parking);
        rent.setStatus(RentStatus.CLOSE);
        rent.setAmount(amount + price.getInit());

        customerRepository.save(customer);
        parkingRepository.save(parking);
        transportRepository.save(transport);
        rentRepository.save(rent);

        return rentMapper.modelToDto(rent);
    }

    private Customer getCustomer() {
        return customerRepository
                .findByLogin(SecurityContext.get().getCustomerLogin())
                .orElseThrow(() -> new ServiceRuntimeException(
                        ErrorCodeEnum.USER_NOT_FOUND,
                        new Throwable(),
                        SecurityContext.get().getCustomerLogin()));
    }

    private Parking getParking(Long id) {
        return parkingRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), id));
    }

    private Transport getTransport(Long id) {
        return transportRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), id));
    }

    private Rent getRent(Long id) {
        return rentRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.RENT_NOT_FOUND, new Throwable(), id));
    }
}