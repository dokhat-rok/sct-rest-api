package com.sct.rest.api.mapper.route;

import com.sct.rest.api.model.dto.route.RentForRoutePointDto;
import com.sct.rest.api.model.entity.RentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PentIdMapper {

    RentForRoutePointDto toDto(RentEntity rent);
}
