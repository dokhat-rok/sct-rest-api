package com.sct.rest.api.mapper.parking;

import com.sct.rest.api.model.dto.transport.ParkingForTransportDto;
import com.sct.rest.api.model.entity.ParkingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingNameMapper {

    ParkingForTransportDto toDto(ParkingEntity parking);
}