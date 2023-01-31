package com.sct.rest.api.service;

import lombok.Getter;

@Getter
public class ServiceRuntimeException extends RuntimeException{
    private final ErrorCodeEnum errorCode;

    public ServiceRuntimeException(ErrorCodeEnum errorCode, Throwable cause, Object... args){
        super(errorCode.getMessage(args), cause);
        this.errorCode = errorCode;
    }
}