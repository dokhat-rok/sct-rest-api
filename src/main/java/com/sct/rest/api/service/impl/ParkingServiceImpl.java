package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.parking.ParkingMapper;
import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.ParkingType;
import com.sct.rest.api.model.filter.ParkingPageableFilter;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.service.ParkingService;
import com.sct.rest.api.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    private final TransportRepository transportRepository;

    private final ParkingMapper parkingMapper;

    @Override
    public List<ParkingDto> getAllParking() {
        return parkingMapper.toListDto(parkingRepository.findAll());
    }

    @Override
    public void createParking(ParkingDto parking) {
        parkingRepository.save(parkingMapper.toModel(parking));
    }

    @Override
    public void updateParking(ParkingDto parkingDto) {
        List<TransportEntity> transportList = new ArrayList<>();
        for (var transport : parkingDto.getTransports()) {
            Optional<TransportEntity> transportOpt = transportRepository
                    .findByIdentificationNumber(transport.getIdentificationNumber());
            transportOpt.ifPresent(transportList::add);
        }
        ParkingEntity parking = parkingMapper.toModel(parkingDto);
        parking.setTransports(transportList);
        parkingRepository.save(parking);
    }

    @Override
    public void addTransport(AddTransportDto addTransport) {
        Optional<ParkingEntity> parkingOptional = parkingRepository.findById(addTransport.getParkingId());
        Optional<TransportEntity> transportOptional = transportRepository.findById(addTransport.getTransportId());

        ParkingEntity parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(
                ErrorCodeEnum.PARKING_NOT_FOUND,
                new Throwable(),
                addTransport.getParkingId()));
        TransportEntity transport = transportOptional.orElseThrow(() -> new ServiceRuntimeException(
                ErrorCodeEnum.TRANSPORT_NOT_FOUND,
                new Throwable(),
                addTransport.getTransportId()));

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        parking.getTransports().add(transport);

        parkingRepository.save(parking);
        transportRepository.save(transport);
    }

    @Override
    public void deleteParking(ParkingDto parkingDto) {
        parkingRepository.delete(parkingMapper.toModel(parkingDto));
    }

    @Override
    public Page<ParkingDto> getAllParkingFilterAndPageable(ParkingPageableFilter filter) {
        ParkingType type = EnumConverter.stringToEnum(ParkingType.class, filter.getType());
        Page<ParkingEntity> entityPage = parkingRepository
                .findAllByFilter(PageRequest.of(filter.getPage(), filter.getSize()), filter.getName(), type);
        return new PageImpl<>(parkingMapper.toListDto(entityPage.getContent()),
                entityPage.getPageable(),
                entityPage.getTotalElements());
    }
}
