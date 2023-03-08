package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {

    private Long id;

    @Valid
    private TransportDto transport;

    private CustomerDto customer;

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
