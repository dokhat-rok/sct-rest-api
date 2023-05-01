package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.transport.TransportMapper;
import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.TransportFilter;
import com.sct.rest.api.model.entity.ParkingEntity;
import com.sct.rest.api.model.entity.TransportEntity;
import com.sct.rest.api.model.enums.Condition;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import com.sct.rest.api.model.filter.TransportPageableFilter;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.service.TransportService;
import com.sct.rest.api.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final ParkingRepository parkingRepository;

    private final TransportRepository transportRepository;

    private final TransportMapper transportMapper;

    @Override
    public List<TransportDto> getAllTransport() {
        return transportRepository.findAll().stream()
                .map(transportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransportDto> getAllTransportByFilter(TransportFilter filter) {
        TransportType type = EnumConverter.stringToEnum(TransportType.class, filter.getType());
        TransportStatus status = EnumConverter.stringToEnum(TransportStatus.class, filter.getStatus());

        return transportMapper.toListDto(transportRepository.findAllByTypeAndStatus(type, status));
    }

    @Override
    public void createTransport(TransportDto transportDto) {
        transportDto.setStatus(TransportStatus.FREE);
        transportDto.setCondition(Condition.EXCELLENT);
        TransportEntity transport = transportRepository.save(transportMapper.toModel(transportDto));

        String ident = transport.getType() == TransportType.BICYCLE ?
                "ВЕЛ-" + transport.getId() :
                "ЭСМ-" + transport.getId();
        transport.setIdentificationNumber(ident);
        if (transport.getType() == TransportType.SCOOTER) {
            transport.setChargePercentage(100L);
            transport.setMaxSpeed(25L);
        }

        ParkingEntity parking = this.getParking(transportDto.getParking().getName());
        if(parking == null) return;

        transport.setCoordinates(parking.getCoordinates());
        transport.setParking(parking);
        parking.getTransports().add(transport);
    }

    @Override
    public void deleteTransport(TransportDto transportDto) {
        TransportEntity transport = this.getTransport(transportDto.getIdentificationNumber());
        transport.setStatus(TransportStatus.UNAVAILABLE);
    }

    @Override
    public Page<TransportDto> getAllTransportFilterAndPageable(TransportPageableFilter filter) {
        Condition condition = EnumConverter.stringToEnum(Condition.class, filter.getCondition());
        TransportStatus status = EnumConverter.stringToEnum(TransportStatus.class, filter.getStatus());
        return transportRepository.findAllByFilter(
                PageRequest.of(filter.getPage(), filter.getSize()),
                filter.getIdentificationNumber(),
                filter.getParkingName(),
                condition,
                status)
                .map(transportMapper::toDto);
    }

    private TransportEntity getTransport(String ident) {
        return transportRepository.findByIdentificationNumber(ident)
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable()));
    }

    private ParkingEntity getParking(String name) {
        return parkingRepository.findByName(name).orElse(null);
    }
}
