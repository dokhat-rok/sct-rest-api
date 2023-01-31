package com.sct.rest.api.model.dto.parking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class AddTransportDto {
    @NotNull
    @PositiveOrZero
    private Long parkingId;
    @NotNull
    @PositiveOrZero
    private Long transportId;
}
