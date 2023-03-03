package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDto getUserById(@PathVariable Long id){
        return customerService.getUserById(id);
    }

    @GetMapping("/current")
    public CustomerDto getCurrent(){
        return customerService.getCurrent();
    }
}