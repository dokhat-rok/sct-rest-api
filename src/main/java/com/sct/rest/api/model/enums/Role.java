package com.sct.rest.api.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    UNKNOWN,

    USER,

    MANAGER,

    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
