package com.sct.rest.api.mapper.rent;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.entity.Rent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentDto modelToDto(Rent rent);
}
