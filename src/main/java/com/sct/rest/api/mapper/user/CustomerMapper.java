package com.sct.rest.api.mapper.user;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerDto modelToDto(Customer customer);
}
