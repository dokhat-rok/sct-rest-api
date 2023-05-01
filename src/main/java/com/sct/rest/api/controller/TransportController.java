package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.TransportFilter;
import com.sct.rest.api.model.filter.TransportPageableFilter;
import com.sct.rest.api.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/transport")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TransportController {

    private final TransportService transportService;

    @GetMapping("/all")
    public ResponseEntity<List<TransportDto>> getAllTransports() {
        List<TransportDto> transports = transportService.getAllTransport();
        log.info("Get transport count: {}", transports.size());
        return ResponseEntity.ok(transports);
    }

    @GetMapping(value = "/all/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransportDto>> getAllTransportsFilter(@NotNull TransportFilter filter) {
        List<TransportDto> transports = transportService.getAllTransportByFilter(filter);
        log.info("Get filter transports count: {}", transports.size());
        return ResponseEntity.ok(transports);
    }

    @GetMapping(value = "/all/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TransportDto>> getAllTransportsFilterAndPageable(TransportPageableFilter filter) {
        Page<TransportDto> page = transportService.getAllTransportFilterAndPageable(filter);
        log.info("Get pageable transports: {}", page);
        return ResponseEntity.ok(page);
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
