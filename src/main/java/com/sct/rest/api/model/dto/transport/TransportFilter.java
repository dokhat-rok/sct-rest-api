package com.sct.rest.api.model.dto.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportFilter {

    private String type;

    private String status;
}
