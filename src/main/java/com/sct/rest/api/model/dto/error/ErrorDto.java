package com.sct.rest.api.model.dto.error;

import com.sct.rest.api.service.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
    private ErrorCodeEnum errorCode;
    private String message;

    public ErrorDto(ErrorCodeEnum errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
