package com.sct.rest.api.mapper.rent;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.entity.RentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentDto modelToDto(RentEntity rent);

    List<RentDto> listModelToListDto(List<RentEntity> rentList);
}
