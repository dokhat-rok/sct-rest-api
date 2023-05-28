package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.rent.RentMapper;
import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripBeginDto;
import com.sct.rest.api.model.dto.trip.TripEndDto;
import com.sct.rest.api.model.entity.CustomerEntity;
import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.entity.RentEntity;
import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.PriceService;
import com.sct.rest.api.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final CustomerRepository customerRepository;

    private final ParkingRepository parkingRepository;

    private final TransportRepository transportRepository;

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    private final PriceService priceService;

    @Override
    public RentDto beginRent(TripBeginDto tripBegin) {
        CustomerEntity customer = this.getCustomer();
        ParkingEntity parking = this.getParking(tripBegin.getParkingId());
        TransportEntity transport = this.getTransport(tripBegin.getTransportId());
        PriceDto price = priceService.getActualPrice(transport.getType());

        if (transport.getParking() != null && transport.getStatus() == TransportStatus.FREE) {
            if (customer.getBalance().intValue() < price.getInit() ||
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

        RentEntity rent = RentEntity.builder()
                .customer(customer)
                .transport(transport)
                .beginTimeRent(ZonedDateTime.now())
                .beginParking(parking)
                .status(RentStatus.OPEN)
                .amount(price.getInit())
                .build();
        rentRepository.save(rent);

        return rentMapper.toDto(rent);
    }

    @Override
    public RentDto endRent(TripEndDto tripEnd) {
        CustomerEntity customer = this.getCustomer();
        ParkingEntity parking = this.getParking(tripEnd.getParkingId());
        TransportEntity transport = this.getTransport(tripEnd.getTransportId());
        RentEntity rent = this.getRent(tripEnd.getRentId());
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
        rent.setAmount(amount + rent.getAmount());

        return rentMapper.toDto(rent);
    }

    private CustomerEntity getCustomer() {
        return customerRepository
                .findByLogin(SecurityContext.get().getCustomerLogin())
                .orElseThrow(() -> new ServiceRuntimeException(
                        ErrorCodeEnum.USER_NOT_FOUND,
                        new Throwable(),
                        SecurityContext.get().getCustomerLogin()));
    }

    private ParkingEntity getParking(Long id) {
        return parkingRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), id));
    }

    private TransportEntity getTransport(Long id) {
        return transportRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable(), id));
    }

    private RentEntity getRent(Long id) {
        return rentRepository
                .findById(id)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.RENT_NOT_FOUND, new Throwable(), id));
    }
}