package com.sct.rest.api.model.filter;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPageableFilter extends PageableFilter {

    private String login;

    private String role;
}
