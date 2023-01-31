package com.sct.rest.api.controller;

import com.sct.rest.api.service.TransportService;
import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.FindTransportDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/transports")
public class TransportController {
    private final TransportService transportService;

    @Autowired
    public TransportController(TransportService transportService){
        this.transportService = transportService;
    }

    @GetMapping
    public List<TransportDto> getAllTransport(){
        return transportService.getAllTransport();
    }

    @GetMapping("/find")
    public List<TransportDto> findTransport(@Validated @RequestBody FindTransportDto findTransportDto){
        return transportService.findTransport(findTransportDto.getType(), findTransportDto.getStatus());
    }

    @PostMapping
    public void createTransport(@Validated @RequestBody TransportDto transportDto){
        transportService.createTransport(transportDto);
    }

    @DeleteMapping
    public void deleteTransport(@RequestBody @Validated TransportDto transportDto){
        transportService.deleteTransport(transportDto);
    }
}
