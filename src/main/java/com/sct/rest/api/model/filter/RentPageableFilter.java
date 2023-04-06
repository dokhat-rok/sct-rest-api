package com.sct.rest.api.model.filter;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RentPageableFilter extends PageableFilter {

    private String login;

    private String transportIdent;

    private String status;
}
