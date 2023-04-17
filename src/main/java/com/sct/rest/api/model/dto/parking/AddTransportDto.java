package com.sct.rest.api.model.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTransportDto {

    @NotBlank
    private String parkingName;

    @NotBlank
    private String transportIdent;
}
