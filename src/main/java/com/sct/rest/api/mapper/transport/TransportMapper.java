package com.sct.rest.api.mapper.transport;

import com.sct.rest.api.mapper.parking.ParkingNameMapper;
import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.entity.Transport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ParkingNameMapper.class)
public interface TransportMapper {

    Transport dtoToModel(TransportDto transportDto);

    TransportDto modelToDto(Transport transport);
}