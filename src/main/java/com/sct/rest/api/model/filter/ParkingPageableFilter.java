package com.sct.rest.api.model.filter;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkingPageableFilter extends PageableFilter {

    private String name;

    private String type;

    private String status;
}
