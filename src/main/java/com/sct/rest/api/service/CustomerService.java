package com.sct.rest.api.service;

import com.sct.rest.api.mapper.user.UserMapper;
import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.Customer;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.security.CallContext;
import com.sct.rest.api.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserMapper userMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserMapper userMapper){
        this.customerRepository = customerRepository;
        this.userMapper = userMapper;
    }

    public CustomerDto getUserById(Long id){
        Optional<Customer> userOptional = customerRepository.findById(id);
        Customer customer = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), id));
        return userMapper.modelToDto(customer);
    }

    public CustomerDto getCurrent(){
        CallContext context = SecurityContext.get();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(context.getUserId());
        customerDto.setLogin(context.getUserLogin());
        customerDto.setRole(context.getUserRole());
        customerDto.setBalance(context.getUserBalance());
        return customerDto;
    }

    public CustomerDto getUserByLogin(String login){
        Optional<Customer> userOptional = Optional.ofNullable(customerRepository.findByLogin(login));
        Customer customer = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), login));
        return userMapper.modelToDto(customer);
    }

    public boolean userExistByLogin(String login){
        Optional<Customer> userOptional = Optional.ofNullable(customerRepository.findByLogin(login));
        return userOptional.isPresent();
    }

    public void createUser(Customer customer){
        customerRepository.save(customer);
    }
}