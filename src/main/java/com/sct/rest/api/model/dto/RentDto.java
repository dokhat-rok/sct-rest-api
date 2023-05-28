package com.sct.rest.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sct.rest.api.model.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime beginTimeRent;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime endTimeRent;

    @Valid
    private ParkingDto beginParking;

    private ParkingDto endParking;

    private List<RoutePointDto> routePoints;

    @NotNull
    private RentStatus status;

    private Long amount;
}
