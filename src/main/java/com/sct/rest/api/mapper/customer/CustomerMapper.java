package com.sct.rest.api.mapper.customer;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerDto modelToDto(CustomerEntity customer);

    List<CustomerDto> listModelToListDto(List<CustomerEntity> customerEntities);
}
