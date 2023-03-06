package com.sct.rest.api.model.dto.transport;

import com.sct.rest.api.model.enums.TransportStatus;
import com.sct.rest.api.model.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportFilter {

    @NotNull(message = "Тип не может быть пустым")
    private TransportType type;

    @NotNull(message = "Статус не может быть пустым")
    private TransportStatus status;
}
