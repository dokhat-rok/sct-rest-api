package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.entity.enums.RentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class RentDto {
    private Long id;
    @Valid
    private TransportDto transport;
    private CustomerDto user;
    @NotNull
    private Timestamp beginTimeRent;
    private Timestamp endTimeRent;
    @Valid
    private ParkingDto beginParking;
    private ParkingDto endParking;
    @NotNull
    private RentStatus status;
    private Long amount;
}
