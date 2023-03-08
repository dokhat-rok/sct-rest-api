package com.sct.rest.api.mapper.parking;

import com.sct.rest.api.model.dto.transport.ParkingNameDto;
import com.sct.rest.api.model.entity.Parking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingNameMapper {

    ParkingNameDto modelToDto(Parking parking);
}