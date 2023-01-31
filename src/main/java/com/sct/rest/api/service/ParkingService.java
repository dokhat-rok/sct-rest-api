package com.sct.rest.api.service;

import com.sct.rest.api.mapper.parking.ParkingMapper;
import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.entity.Parking;
import com.sct.rest.api.model.entity.Transport;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final TransportRepository transportRepository;
    private final ParkingMapper parkingMapper;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository, TransportRepository transportRepository, ParkingMapper parkingMapper){
        this.parkingRepository = parkingRepository;
        this.transportRepository = transportRepository;
        this.parkingMapper = parkingMapper;
    }

    public List<ParkingDto> getAllParking(){
        Iterable<Parking> parkingIterable = parkingRepository.findAll();
        List<ParkingDto> parkingDtoList = new ArrayList<>();
        for(var parking: parkingIterable){
            parkingDtoList.add(parkingMapper.modelToDto(parking));
        }
        return parkingDtoList;
    }

    public void createParking(ParkingDto parkingDto){
        parkingRepository.save(parkingMapper.dtoToModel(parkingDto));
    }

    public void updateParking(ParkingDto parkingDto){
        List<Transport> transportList = new ArrayList<>();
        for(var transportDto: parkingDto.getTransports()){
            Optional<Transport> transportOptional = transportRepository.findById(transportDto.getId());
            transportOptional.ifPresent(transportList::add);
        }
        Parking parking = parkingMapper.dtoToModel(parkingDto);
        parking.setTransports(transportList);
        parkingRepository.save(parking);
    }

    public void addTransport(AddTransportDto addTransportDto){
        Optional<Parking> parkingOptional = parkingRepository.findById(addTransportDto.getParkingId());
        Optional<Transport> transportOptional = transportRepository.findById(addTransportDto.getTransportId());

        Parking parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.PARKING_NOT_FOUND, new Throwable(), addTransportDto.getParkingId()));
        Transport transport = transportOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.TRANSPORT_NOT_FOUND, new Throwable(), addTransportDto.getTransportId()));

        transport.setParking(parking);
        transport.setCoordinates(parking.getCoordinates());
        parking.getTransports().add(transport);

        parkingRepository.save(parking);
        transportRepository.save(transport);
    }

    public void deleteParking(ParkingDto parkingDto){
        parkingRepository.delete(parkingMapper.dtoToModel(parkingDto));
    }
}
