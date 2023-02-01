package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.entity.enums.ParkingType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
public class ParkingDto {
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String name;
    @NotBlank
    @Size(max = 20)
    private String coordinates;
    @NotNull
    @PositiveOrZero
    private Long allowedRadius;
    @NotNull(message = "Тип не может быть пустым")
    private ParkingType type;
    private List<TransportDto> transports;
}
