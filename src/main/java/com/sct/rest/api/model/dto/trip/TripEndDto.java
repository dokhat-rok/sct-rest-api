package com.sct.rest.api.model.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripEndDto {

    @NotNull(message = "Id парковки не может быть пустым")
    private Long parkingId;

    @NotNull(message = "Id транспорта не может быть пустым")
    private Long transportId;

    @NotNull(message = "Id аренды не может быть пустым")
    private Long rentId;
}
