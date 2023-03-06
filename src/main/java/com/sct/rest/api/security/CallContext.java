package com.sct.rest.api.security;

import com.sct.rest.api.model.enums.Role;
import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CallContext {

    private String customerLogin;

    private String customerPassword;

    private Role customerRole;
}
