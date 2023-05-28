package com.sct.rest.api.model.filter;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransportPageableFilter extends PageableFilter {

    private String identificationNumber;

    private String parkingName;

    private String condition;

    private String status;
}
