package com.sct.rest.api.service.impl;

import com.sct.rest.api.mapper.route.RoutePointMapper;
import com.sct.rest.api.model.dto.RoutePointDto;
import com.sct.rest.api.repository.RoutePointRepository;
import com.sct.rest.api.service.RoutePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class RoutePointServiceImpl implements RoutePointService {

    private final RoutePointRepository routePointRepository;

    private final RoutePointMapper routePointMapper;

    @Override
    public void save(RoutePointDto routePoint) {
        routePoint.setCreated_date(ZonedDateTime.now());
        routePointRepository.save(routePointMapper.toModel(routePoint));
    }
}
