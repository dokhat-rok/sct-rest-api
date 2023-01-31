package com.sct.rest.api.model.dto.trip;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TripInputEndDto {
    @NotNull(message = "Id парковки не может быть пустым")
    private Long parkingId;
    @NotNull(message = "Id транспорта не может быть пустым")
    private Long transportId;
    @NotNull(message = "Id аренды не может быть пустым")
    private Long rentId;
}
