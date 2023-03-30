package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/customer")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getUserById(@PathVariable @Positive Long id) {
        CustomerDto customer = customerService.getUserById(id);
        log.info("Get customer by id {}: {}", id, customer);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/current")
    public ResponseEntity<CustomerDto> getCurrent() {
        CustomerDto customer = customerService.getCurrent();
        log.info("Get current customer {}", customer);
        return ResponseEntity.ok(customer);
    }
}