package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripInputBeginDto;
import com.sct.rest.api.model.dto.trip.TripInputEndDto;
import com.sct.rest.api.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/trip")

public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService){
        this.tripService = tripService;
    }

    @GetMapping
    public List<RentDto> getAllRent(){
        return tripService.getAllRent();
    }

    @PostMapping("/begin")
    public RentDto beginTrip(@RequestBody @Validated TripInputBeginDto tripInputBeginDto){
        return tripService.beginRent(tripInputBeginDto);
    }

    @PutMapping("/end")
    public void endTrip(@RequestBody @Validated TripInputEndDto tripInputEndDto){
        tripService.endRent(tripInputEndDto);
    }
}