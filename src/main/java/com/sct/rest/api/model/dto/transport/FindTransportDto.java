package com.sct.rest.api.model.dto.transport;

import com.sct.rest.api.model.entity.TransportStatus;
import com.sct.rest.api.model.entity.TransportType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FindTransportDto {
    @NotNull(message = "Тип не может быть пустым")
    private TransportType type;
    @NotNull(message = "Статус не может быть пустым")
    private TransportStatus status;
}
