package com.sct.rest.api.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeEnum {
    INTERNAL_SERVER_ERROR("Внутренняя ошибка сервиса"),
    USER_NOT_FOUND("Пользователь не найден, id={0}", HttpStatus.NOT_FOUND),
    PARKING_NOT_FOUND("Парковка не найдена, id={0}", HttpStatus.NOT_FOUND),
    TRANSPORT_NOT_FOUND("Транспорт не найден, id={0}", HttpStatus.NOT_FOUND),
    RENT_NOT_FOUND("Аренда не найдена, id={0}", HttpStatus.NOT_FOUND),
    TRANSPORT_NOT_AVAILABLE("Транспорт недоступен"),
    NO_MONEY("Недостаточно денежных средств"),
    VALIDATION_ERROR("Ошибка валидации"),
    ERROR_AUTHORIZATION("Ошибка авториразции", HttpStatus.UNAUTHORIZED);
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final String messageTemplate;


    ErrorCodeEnum(String messageTemplate, HttpStatus httpStatus){
        this.messageTemplate = messageTemplate;
        this.httpStatus = httpStatus;
    }

    public String getMessage(Object... args){
        return MessageFormat.format(messageTemplate, args);
    }
}
