package com.sct.rest.api.model.dto.security;

import com.sct.rest.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CallContext {

    private String customerLogin;

    private String customerPassword;

    private Role customerRole;
}
