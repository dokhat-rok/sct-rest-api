package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.parking.ParkingMapper;
import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.entity.Parking;
import com.sct.rest.api.model.entity.Transport;
import com.sct.rest.api.repository.ParkingRepository;
import com.sct.rest.api.repository.TransportRepository;
import com.sct.rest.api.service.ParkingService;
import lombok.RequiredArgsConstructor;
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
        return parkingMapper.listModelToListDto(parkingRepository.findAll());
    }

    @Override
    public void createParking(ParkingDto parking) {
        parkingRepository.save(parkingMapper.dtoToModel(parking));
    }

    @Override
    public void updateParking(ParkingDto parkingDto) {
        List<Transport> transportList = new ArrayList<>();
        for (var transport : parkingDto.getTransports()) {
            Optional<Transport> transportOpt = transportRepository
                    .findByIdentificationNumber(transport.getIdentificationNumber());
            transportOpt.ifPresent(transportList::add);
        }
        Parking parking = parkingMapper.dtoToModel(parkingDto);
        parking.setTransports(transportList);
        parkingRepository.save(parking);
    }

    @Override
    public void addTransport(AddTransportDto addTransport) {
        Optional<Parking> parkingOptional = parkingRepository.findById(addTransport.getParkingId());
        Optional<Transport> transportOptional = transportRepository.findById(addTransport.getTransportId());

        Parking parking = parkingOptional.orElseThrow(() -> new ServiceRuntimeException(
                ErrorCodeEnum.PARKING_NOT_FOUND,
                new Throwable(),
                addTransport.getParkingId()));
        Transport transport = transportOptional.orElseThrow(() -> new ServiceRuntimeException(
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
        parkingRepository.delete(parkingMapper.dtoToModel(parkingDto));
    }
}
