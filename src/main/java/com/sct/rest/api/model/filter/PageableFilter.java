package com.sct.rest.api.model.filter;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableFilter {

    @Min(0)
    private Integer page;

    @Min(1)
    @Max(100)
    private Integer size;
}
