package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.enums.TransportType;
import com.sct.rest.api.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/price")
@RequiredArgsConstructor
@Slf4j
public class PriceController {

    private final PriceService priceService;

    @GetMapping("/{type}")
    public ResponseEntity<PriceDto> getActualPrice(@PathVariable TransportType type) {
        PriceDto price = priceService.getActualPrice(type);
        log.info("Get actual price: {}", price);
        return ResponseEntity.ok(price);
    }

    @PutMapping("/{type}")
    public ResponseEntity<PriceDto> setPrice(@PathVariable TransportType type, @RequestBody PriceDto price) {
        PriceDto actualPrice = priceService.setPrice(type, price);
        log.info("Set actual price: {}", actualPrice);
        return ResponseEntity.ok(actualPrice);
    }
}
