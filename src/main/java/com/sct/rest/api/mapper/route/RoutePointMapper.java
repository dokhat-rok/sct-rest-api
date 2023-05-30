package com.sct.rest.api.mapper.route;

import com.sct.rest.api.model.dto.RoutePointDto;
import com.sct.rest.api.model.entity.RoutePointEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoutePointMapper {

    RoutePointDto toDto(RoutePointEntity routePoint);

    RoutePointEntity toModel(RoutePointDto routePoint);

    List<RoutePointDto> toListDto(List<RoutePointEntity> routePointList);
}
