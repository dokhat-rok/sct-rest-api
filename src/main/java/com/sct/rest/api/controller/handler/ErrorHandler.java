package com.sct.rest.api.controller.handler;

import com.sct.rest.api.model.dto.error.ErrorDto;
import com.sct.rest.api.service.ErrorCodeEnum;
import com.sct.rest.api.service.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Validated
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<ErrorDto> handleBusinessException(ServiceRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ErrorDto errorDto = new ErrorDto(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorDto, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleAnyException(Throwable ex) {
        log.error(ex.getMessage(), ex);
        ErrorDto error = new ErrorDto(ErrorCodeEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> sb.append(String.format(
                        "Поле %s.%s:%s;",
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getDefaultMessage())));
        log.warn("{}: {}", ErrorCodeEnum.VALIDATION_ERROR.getMessageTemplate(), sb);
        return new ErrorDto(ErrorCodeEnum.VALIDATION_ERROR, sb.toString());
    }
}
