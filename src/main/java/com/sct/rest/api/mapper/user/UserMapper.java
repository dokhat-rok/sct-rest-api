package com.sct.rest.api.mapper.user;

import com.sct.rest.api.model.dto.UserDto;
import com.sct.rest.api.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto modelToDto(User user);
}
