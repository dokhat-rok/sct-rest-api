package com.sct.rest.api.model.dto.transport;

import com.sct.rest.api.model.entity.enums.TransportStatus;
import com.sct.rest.api.model.entity.enums.TransportType;
import lombok.*;

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
