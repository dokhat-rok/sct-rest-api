package com.sct.rest.api.mapper.rent;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.entity.RentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentDto toDto(RentEntity rent);

    List<RentDto> toListDto(List<RentEntity> rentList);
}
