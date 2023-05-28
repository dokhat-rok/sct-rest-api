package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.filter.CustomerPageableFilter;
import com.sct.rest.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/all/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CustomerDto>> getAllCustomerFilterAndPageable(CustomerPageableFilter filter) {
        Page<CustomerDto> page = customerService.getAllCustomerFilterAndPageable(filter);
        log.info("Get pageable customer: {}", page);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteCurrent() {
        log.info("Delete current customer: {}", customerService.getCurrent().getLogin());
        customerService.deleteCurrent();
        return ResponseEntity.ok().build();
    }
}