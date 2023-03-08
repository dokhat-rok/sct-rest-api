package com.sct.rest.api.exception;

import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class ServiceRuntimeException extends RuntimeException {
    private final ErrorCodeEnum errorCode;

    public ServiceRuntimeException(ErrorCodeEnum errorCode, Throwable cause, Object... args) {
        super(errorCode.getMessage(args), cause);
        this.errorCode = errorCode;
    }
}