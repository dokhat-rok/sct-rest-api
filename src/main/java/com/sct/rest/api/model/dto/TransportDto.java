package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.dto.transport.ParkingNameDto;
import com.sct.rest.api.model.entity.Condition;
import com.sct.rest.api.model.entity.TransportStatus;
import com.sct.rest.api.model.entity.TransportType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
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