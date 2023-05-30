package com.sct.rest.api.model.dto;

import com.sct.rest.api.model.dto.route.RentForRoutePointDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoutePointDto {

    private RentForRoutePointDto rent;

    private Double latitude;

    private Double longitude;

    private ZonedDateTime createdDate;
}
