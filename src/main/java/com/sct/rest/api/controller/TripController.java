package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripBeginDto;
import com.sct.rest.api.model.dto.trip.TripEndDto;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;

    @GetMapping("/all")
    public ResponseEntity<List<RentDto>> getAllRent() {
        List<RentDto> rents = tripService.getAllRent();
        log.info("Get all rents count {}", rents.size());
        return ResponseEntity.ok(rents);
    }

    @PostMapping("/begin")
    public ResponseEntity<RentDto> beginTrip(@RequestBody TripBeginDto tripBegin) {
        RentDto rent = tripService.beginRent(tripBegin);
        log.info("Customer {} begin rent id {}", rent.getCustomer().getLogin(), rent.getId());
        return ResponseEntity.ok(rent);
    }

    @PutMapping("/end")
    public ResponseEntity<Void> endTrip(@RequestBody TripEndDto tripEnd) {
        tripService.endRent(tripEnd);
        log.info("Customer {} end rent id {}", SecurityContext.get().getCustomerLogin(), tripEnd.getRentId());
        return ResponseEntity.ok().build();
    }
}