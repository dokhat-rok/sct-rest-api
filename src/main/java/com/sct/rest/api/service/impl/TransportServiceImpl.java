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
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final ParkingRepository parkingRepository;

    private final TransportRepository transportRepository;

    private final TransportMapper transportMapper;

    @Override
    public List<TransportDto> getAllTransportByFilter(TransportFilter filter) {
        TransportType type = null;
        try {
            type = TransportType.valueOf(filter.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceRuntimeException(ErrorCodeEnum.VALIDATION_ERROR, new Throwable());
        } catch (NullPointerException ignored) {
        }

        TransportStatus status = null;
        try {
            status = TransportStatus.valueOf(filter.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceRuntimeException(ErrorCodeEnum.VALIDATION_ERROR, new Throwable());
        } catch (NullPointerException ignored) {
        }

        if (status != null && type != null) return this.getAllTransportByTypeAndStatus(type, status);
        if (status != null) return this.getAllTransportByStatus(status);
        if (type != null) return this.getAllTransportByType(type);
        return this.getAllTransport();
    }

    @Override
    public void createTransport(TransportDto transportDto) {
        transportDto.setStatus(TransportStatus.FREE);
        transportDto.setCondition(Condition.EXCELLENT);
        TransportEntity transport = transportRepository.save(transportMapper.dtoToModel(transportDto));

        String ident = transport.getType() == TransportType.BICYCLE ?
                "ВЕЛ-" + transport.getId() :
                "ЭСМ-" + transport.getId();
        transport.setIdentificationNumber(ident);
        if (transport.getType() == TransportType.SCOOTER) {
            transport.setChargePercentage(100L);
            transport.setMaxSpeed(25L);
        }

        Optional<ParkingEntity> parkingOptional = parkingRepository.findByName(transportDto.getParking().getName());
        ParkingEntity parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(
                ErrorCodeEnum.PARKING_NOT_FOUND,
                new Throwable(),
                transportDto.getParking().getName()));

        transport.setCoordinates(parking.getCoordinates());
        transport.setParking(parking);
        parking.getTransports().add(transport);

        transportRepository.save(transport);
        parkingRepository.save(parking);
    }

    @Override
    public void deleteTransport(TransportDto transportDto) {
        transportDto.setStatus(TransportStatus.UNAVAILABLE);
        transportRepository.save(transportMapper.dtoToModel(transportDto));
    }

    private List<TransportDto> getAllTransport() {
        return transportMapper.listModelToListDto(transportRepository.findAll());
    }

    private List<TransportDto> getAllTransportByStatus(TransportStatus status) {
        return transportMapper.listModelToListDto(transportRepository.findAllByStatus(status));
    }

    private List<TransportDto> getAllTransportByType(TransportType type) {
        return transportMapper.listModelToListDto(transportRepository.findAllByType(type));
    }

    private List<TransportDto> getAllTransportByTypeAndStatus(TransportType type, TransportStatus status) {
        return transportMapper.listModelToListDto(transportRepository.findAllByTypeAndStatus(type, status));
    }
}
