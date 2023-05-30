package com.sct.rest.api.service.impl;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.mapper.customer.CustomerMapper;
import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.CustomerEntity;
import com.sct.rest.api.model.enums.CustomerStatus;
import com.sct.rest.api.model.enums.Role;
import com.sct.rest.api.model.filter.CustomerPageableFilter;
import com.sct.rest.api.repository.CustomerRepository;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.CustomerService;
import com.sct.rest.api.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return customerMapper.toDto(customer);
    }

    @Override
    public CustomerDto getCurrent() {
        CustomerDto customer = this.getUserByLogin(SecurityContext.get().getCustomerLogin());
        customer.setTripCount(rentRepository.countRentByCustomerLogin(customer.getLogin()));
        return customer;
    }

    @Override
    public CustomerDto getUserByLogin(String login) {
        return customerMapper.toDto(this.getCurrentCustomer(login));
    }

    @Override
    public CustomerDto additionalBalance(Long amount) {
        CustomerEntity customer = this.getCurrentCustomer(SecurityContext.get().getCustomerLogin());
        customer.setBalance(customer.getBalance() + amount);
        return this.getCurrent();
    }

    @Override
    public void deleteCurrent() {
        CustomerEntity customer = this.getCurrentCustomer(SecurityContext.get().getCustomerLogin());
        customer.setStatus(CustomerStatus.NON_ACTIVE);
    }

    @Override
    public Page<CustomerDto> getAllCustomerFilterAndPageable(CustomerPageableFilter filter) {
        Role role = EnumConverter.stringToEnum(Role.class, filter.getRole());

        if(filter.getLogin() != null) filter.setLogin(filter.getLogin().toLowerCase());

        return customerRepository
                .findAllByFilter(PageRequest.of(filter.getPage(), filter.getSize()), filter.getLogin(), role)
                .map(customerMapper::toDto)
                .map(c -> {
                    c.setTripCount(rentRepository.countRentByCustomerLogin(c.getLogin()));
                    return c;
                });
    }

    private CustomerEntity getCurrentCustomer(String login) {
        Optional<CustomerEntity> userOptional = customerRepository.findByLogin(login);
        return userOptional
                .orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), login));
    }
}