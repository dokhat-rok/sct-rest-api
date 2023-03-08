package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.dto.transport.ParkingNameDto;
import com.sct.rest.api.model.enums.Condition;
import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransportDto {

    private Long id;

    @NotNull(message = "Тип не может быть пустой")
    private TransportType type;

    private String identificationNumber;

    private String coordinates;

    private Condition condition;

    private TransportStatus status;

    private Long chargePercentage;

    private Long maxSpeed;

    private ParkingNameDto parking;
}