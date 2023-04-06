package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.filter.ParkingPageableFilter;
import com.sct.rest.api.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/parking")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/all")
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        List<ParkingDto> parkingList = parkingService.getAllParking();
        log.info("Get all parking count: {}", parkingList.size());
        return ResponseEntity.ok(parkingList);
    }

    @GetMapping(value = "/all/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ParkingDto>> getAllParkingFilterAndPageable(ParkingPageableFilter filter) {
        Page<ParkingDto> page = parkingService.getAllParkingFilterAndPageable(filter);
        log.info("Get pageable parking: {}", page);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Void> createParking(@RequestBody ParkingDto parking) {
        parkingService.createParking(parking);
        log.info("Create parking {}#{}", parking.getName(), parking.getType());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateParking(@RequestBody ParkingDto parking) {
        parkingService.updateParking(parking);
        log.info("Update parking {}#{}", parking.getName(), parking.getType());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/add/transport")
    public ResponseEntity<Void> addTransport(@RequestBody AddTransportDto addTransport) {
        parkingService.addTransport(addTransport);
        log.info("Add transport {} to parking {}", addTransport.getTransportId(), addTransport.getParkingId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteParking(@RequestBody ParkingDto parking) {
        parkingService.deleteParking(parking);
        log.info("Delete parking {}#{}", parking.getName(), parking.getType());
        return ResponseEntity.ok().build();
    }


}