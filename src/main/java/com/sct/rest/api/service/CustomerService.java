package com.sct.rest.api.service;

import com.sct.rest.api.mapper.user.CustomerMapper;
import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.Customer;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.security.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final RentRepository rentRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto getUserById(Long id){
        Optional<Customer> userOptional = customerRepository.findById(id);
        Customer customer = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), id));
        return customerMapper.modelToDto(customer);
    }

    public CustomerDto getCurrent(){
        CustomerDto customer = this.getUserByLogin(SecurityContext.get().getCustomerLogin());
        customer.setTripCount(rentRepository.countRentByCustomerLogin(customer.getLogin()));
        return customer;
    }

    public CustomerDto getUserByLogin(String login){
        Optional<Customer> userOptional = customerRepository.findByLogin(login);
        Customer customer = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), login));
        return customerMapper.modelToDto(customer);
    }

    public boolean userExistByLogin(String login){
        Optional<Customer> userOptional = customerRepository.findByLogin(login);
        return userOptional.isPresent();
    }

    public void createUser(Customer customer){
        customerRepository.save(customer);
    }
}