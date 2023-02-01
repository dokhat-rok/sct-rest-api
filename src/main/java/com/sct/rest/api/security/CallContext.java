package com.sct.rest.api.security;

import com.sct.rest.api.model.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CallContext {
    private Long userId;
    private String userLogin;
    private Role userRole;
    private Long userBalance;
}
