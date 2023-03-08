package com.sct.rest.api.mapper.parking;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.entity.Parking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingMapper {

    Parking dtoToModel(ParkingDto parkingDto);

    ParkingDto modelToDto(Parking parking);

    List<ParkingDto> listModelToListDto(List<Parking> parkingList);
}
