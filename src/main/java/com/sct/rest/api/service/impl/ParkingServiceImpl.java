package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.parking.ParkingMapper;
import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.ParkingStatus;
import com.sct.rest.api.model.enums.ParkingType;
import com.sct.rest.api.model.filter.ParkingPageableFilter;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.service.ParkingService;
import com.sct.rest.api.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
        parking.setStatus(ParkingStatus.ACTIVE);
        if(parking.getName().equals("Новая парковка")) parking.setName("ПАРК");
        ParkingEntity parkingEntity = parkingRepository.save(parkingMapper.toModel(parking));
        parkingEntity.setName(parkingEntity.getName() + "-" + parkingEntity.getId());
    }

    @Override
    public void updateParking(ParkingDto parkingDto) {
        ParkingEntity parking = this.getParking(parkingDto.getName());
        parking.setName(parkingDto.getName());
        parking.setCoordinates(parkingDto.getCoordinates());
        parking.setStatus(parkingDto.getStatus());
        parking.setAllowedRadius(parkingDto.getAllowedRadius());

        if(parking.getStatus() == ParkingStatus.ACTIVE) return;

        parking.getTransports().forEach(t -> t.setParking(null));
    }

    @Override
    public void addTransport(AddTransportDto addTransport) {
        ParkingEntity parking = this.getParkingForUser(addTransport.getParkingName());
        TransportEntity transport = this.getTransport(addTransport.getTransportIdent());

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        parking.getTransports().add(transport);
    }

    @Override
    public void deleteParking(ParkingDto parkingDto) {
        ParkingEntity parking = this.getParking(parkingDto.getName());
        parking.setStatus(ParkingStatus.NON_ACTIVE);
    }

    @Override
    public Page<ParkingDto> getAllParkingFilterAndPageable(ParkingPageableFilter filter) {
        ParkingType type = EnumConverter.stringToEnum(ParkingType.class, filter.getType());
        ParkingStatus status = EnumConverter.stringToEnum(ParkingStatus.class, filter.getStatus());
        if(filter.getName() != null) filter.setName(filter.getName().toLowerCase());
        return parkingRepository
                .findAllByFilter(PageRequest.of(filter.getPage(), filter.getSize()), filter.getName(), type, status)
                .map(parkingMapper::toDto);
    }

    private ParkingEntity getParkingForUser(String name) {
        return parkingRepository.findByNameForUser(name)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable()));
    }

    private ParkingEntity getParking(String name) {
        return parkingRepository.findByName(name)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable()));
    }

    private TransportEntity getTransport(String ident) {
        return transportRepository.findByIdentificationNumber(ident)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable()));
    }
}
