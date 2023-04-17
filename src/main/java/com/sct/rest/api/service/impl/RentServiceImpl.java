package com.sct.rest.api.service.impl;

import com.sct.rest.api.mapper.rent.RentMapper;
import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.entity.RentEntity;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.model.filter.RentPageableFilter;
import com.sct.rest.api.repository.RentRepository;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.service.RentService;
import com.sct.rest.api.util.EnumConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    @Override
    public List<RentDto> getAllRent() {
        return rentMapper.toListDto(rentRepository.findAll());
    }

    @Override
    public List<RentDto> getAllRentForCurrentUserByStatus(RentStatus status) {
        return rentMapper
                .toListDto(rentRepository
                        .findAllByCustomerLoginAndStatus(SecurityContext.get().getCustomerLogin(), status));
    }

    @Override
    public Page<RentDto> getAllRentFilterAndPageable(RentPageableFilter filter) {
        RentStatus status = EnumConverter.stringToEnum(RentStatus.class, filter.getStatus());
        return rentRepository.findAllByFilter(
                PageRequest.of(filter.getPage(), filter.getSize()),
                filter.getLogin(),
                filter.getTransportIdent(), status)
                .map(rentMapper::toDto);
    }
}
