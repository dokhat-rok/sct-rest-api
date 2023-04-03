package com.sct.rest.api.mapper.parking;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.entity.ParkingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParkingMapper {

    ParkingEntity dtoToModel(ParkingDto parkingDto);

    ParkingDto modelToDto(ParkingEntity parking);

    List<ParkingDto> listModelToListDto(List<ParkingEntity> parkingList);
}
