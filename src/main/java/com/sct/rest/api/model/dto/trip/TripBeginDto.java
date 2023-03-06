package com.sct.rest.api.model.dto.trip;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripBeginDto {

    @NotNull(message = "Id парковки не может быть пустым")
    private Long parkingId;

    @NotNull(message = "Id транспорта не может быть пустым")
    private Long transportId;
}
