package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @NotBlank
    private String login;

    @NotNull
    @PositiveOrZero
    private Long balance;

    @NotNull(message = "Роль не может быть пустой")
    private Role role;

    @PositiveOrZero
    private Long tripCount;
}
