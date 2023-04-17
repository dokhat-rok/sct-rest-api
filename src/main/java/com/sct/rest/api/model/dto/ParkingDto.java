package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.enums.ParkingStatus;
import com.sct.rest.api.model.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String coordinates;

    @NotNull
    @Positive
    private Long allowedRadius;

    @NotNull(message = "Тип не может быть пустым")
    private ParkingType type;

    private ParkingStatus status;

    private List<TransportDto> transports;
}
