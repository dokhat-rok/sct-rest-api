package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.entity.enums.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @NotBlank
    @Size(max = 20)
    private String login;

    @NotNull
    @PositiveOrZero
    private Long balance;

    @NotNull(message = "Роль не может быть пустой")
    private Role role;

    private Long tripCount;
}
