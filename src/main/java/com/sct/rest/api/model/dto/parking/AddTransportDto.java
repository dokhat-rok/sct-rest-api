package com.sct.rest.api.model.dto.parking;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTransportDto {

    @NotNull
    @PositiveOrZero
    private Long parkingId;

    @NotNull
    @PositiveOrZero
    private Long transportId;
}
