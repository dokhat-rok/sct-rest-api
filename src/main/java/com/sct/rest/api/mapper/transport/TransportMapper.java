package com.sct.rest.api.mapper.transport;

import com.sct.rest.api.mapper.parking.ParkingNameMapper;
import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.entity.TransportEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ParkingNameMapper.class)
public interface TransportMapper {

    TransportEntity toModel(TransportDto transportDto);

    TransportDto toDto(TransportEntity transportEntity);

    List<TransportDto> toListDto(List<TransportEntity> transportList);
}