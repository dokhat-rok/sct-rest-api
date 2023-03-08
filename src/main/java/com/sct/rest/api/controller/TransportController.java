package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.TransportFilter;
import com.sct.rest.api.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transport")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TransportController {

    private final TransportService transportService;

    @GetMapping("/all/filter")
    public ResponseEntity<List<TransportDto>> getAllTransports(@RequestParam TransportFilter filter) {
        List<TransportDto> transports = transportService.getAllTransportByFilter(filter);
        log.info("Get transport count: {}", transports.size());
        return ResponseEntity.ok(transports);
    }

    @PostMapping
    public ResponseEntity<Void> createTransport(@RequestBody TransportDto transport) {
        transportService.createTransport(transport);
        log.info("Create transport {}", transport.getType());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransport(@RequestBody TransportDto transport) {
        transportService.deleteTransport(transport);
        log.info("Delete transport {}", transport.getIdentificationNumber());
        return ResponseEntity.ok().build();
    }
}
