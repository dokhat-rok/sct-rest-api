package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.service.ParkingService;
import com.sct.rest.api.model.dto.ParkingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService){
        this.parkingService = parkingService;
    }

    @GetMapping
    public List<ParkingDto> getAllParking(){
        return parkingService.getAllParking();
    }

    @PostMapping
    public void createParking(@RequestBody @Validated ParkingDto parkingDto){
        parkingService.createParking(parkingDto);
    }

    @PutMapping
    public void updateParking(@RequestBody @Validated ParkingDto parkingDto){
        parkingService.updateParking(parkingDto);
    }

    @PutMapping("/addTransport")
    public void addTransport(@RequestBody @Validated AddTransportDto addTransportDto){
        parkingService.addTransport(addTransportDto);
    }

    @DeleteMapping
    public void deleteParking(@RequestBody @Validated ParkingDto parkingDto){
        parkingService.deleteParking(parkingDto);
    }
}
