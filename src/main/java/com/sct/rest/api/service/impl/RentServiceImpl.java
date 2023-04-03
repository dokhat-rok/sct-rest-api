package com.sct.rest.api.service.impl;

import com.sct.rest.api.mapper.rent.RentMapper;
import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    @Override
    public List<RentDto> getAllRent() {
        return rentMapper.listModelToListDto(rentRepository.findAll());
    }

    @Override
    public List<RentDto> getAllRentForCurrentUserByStatus(RentStatus status) {
        return rentMapper
                .listModelToListDto(rentRepository
                        .findAllByCustomerLoginAndStatus(SecurityContext.get().getCustomerLogin(), status));
    }
}