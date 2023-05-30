package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.RoutePointDto;
import com.sct.rest.api.service.RoutePointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/route/point")
@RequiredArgsConstructor
@Slf4j
public class RoutePointController {

    private final RoutePointService routePointService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RoutePointDto routePoint) {
        routePointService.save(routePoint);
        log.info("Save point {}", routePoint);
        return ResponseEntity.ok().build();
    }
}
