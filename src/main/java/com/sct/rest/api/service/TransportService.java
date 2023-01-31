package com.sct.rest.api.service;

import com.sct.rest.api.mapper.transport.TransportMapper;
import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.ParkingNameDto;
import com.project.model.entity.*;
import com.sct.rest.api.model.entity.*;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransportService {
    private final ParkingRepository parkingRepository;
    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    @Autowired
    public TransportService(ParkingRepository parkingRepository, TransportRepository transportRepository, TransportMapper transportMapper){
        this.parkingRepository = parkingRepository;
        this.transportRepository = transportRepository;
        this.transportMapper = transportMapper;
    }

    public List<TransportDto> getAllTransport(){
        Iterable<Parking> parkingIterable = parkingRepository.findAll();
        List<TransportDto> transportDtoList = new ArrayList<>();
        for(var parking: parkingIterable){
            for(var transport : parking.getTransports()){
                TransportDto transportDto = transportMapper.modelToDto(transport);
                transportDtoList.add(transportDto);
            }
        }
        return transportDtoList;
    }

    public List<TransportDto> findTransport(TransportType type, TransportStatus status){
        Iterable<Transport> transportIterable = transportRepository.findAll();
        List<TransportDto> transportDtoList = new ArrayList<>();
        for(var transport: transportIterable){
            if(transport.getType() == type && transport.getStatus() == status){
                transportDtoList.add(transportMapper.modelToDto(transport));
            }
        }
        return transportDtoList;
    }

    public List<TransportDto> findTransport(TransportStatus status){
        Iterable<Transport> transportIterable = transportRepository.findAll();
        List<TransportDto> transportDtoList = new ArrayList<>();
        for(var transport: transportIterable){
            if(transport.getStatus() == status){
                transportDtoList.add(transportMapper.modelToDto(transport));
            }
        }
        return transportDtoList;
    }

    public void createTransport(TransportDto transportDto){
        transportDto.setStatus(TransportStatus.FREE);
        transportDto.setCondition(Condition.EXCELLENT);
        ParkingNameDto parkingName = new ParkingNameDto();
        parkingName.setName(transportDto.getParking().getName());
        transportDto.setParking(null);
        Transport transport = transportRepository.save(transportMapper.dtoToModel(transportDto));
        transportDto.setParking(parkingName);
        if(transport.getType() == TransportType.BICYCLE){
            transport.setIdentificationNumber("ВЕЛ-" + transport.getId());
        }
        else
        {
            transport.setIdentificationNumber("ЭСМ-" + transport.getId());
            transport.setChargePercentage(100L);
            transport.setMaxSpeed(25L);
        }
        Optional<Parking> parkingOptional = parkingRepository.findById(Long.parseLong(transportDto.getParking().getName()));
        Parking parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), transportDto.getParking().getName()));
        parking.getTransports().add(transport);
        transport.setCoordinates(parking.getCoordinates());
        transport.setParking(parking);

        transportRepository.save(transport);
        parkingRepository.save(parking);
    }

    public void deleteTransport(TransportDto transportDto){
        transportDto.setStatus(TransportStatus.UNAVAILABLE);
        transportRepository.save(transportMapper.dtoToModel(transportDto));
    }
}
