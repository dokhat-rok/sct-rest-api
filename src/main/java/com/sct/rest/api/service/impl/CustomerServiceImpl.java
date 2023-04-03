package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.customer.CustomerMapper;
import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.CustomerEntity;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final RentRepository rentRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto getUserById(Long id) {
        Optional<CustomerEntity> userOptional = customerRepository.findById(id);
        CustomerEntity customer = userOptional
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), id));
        return customerMapper.modelToDto(customer);
    }

    @Override
    public CustomerDto getCurrent() {
        CustomerDto customer = this.getUserByLogin(SecurityContext.get().getCustomerLogin());
        customer.setTripCount(rentRepository.countRentByCustomerLogin(customer.getLogin()));
        return customer;
    }

    @Override
    public CustomerDto getUserByLogin(String login) {
        Optional<CustomerEntity> userOptional = customerRepository.findByLogin(login);
        CustomerEntity customer = userOptional
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), login));
        return customerMapper.modelToDto(customer);
    }
}