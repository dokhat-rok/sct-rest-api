package com.sct.rest.api.util;

import com.sct.rest.api.exception.ServiceRuntimeException;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;

public class EnumConverter {

    public static <E extends Enum<E>> E stringToEnum(Class<E> enumClass, String name) {
        E enumValue;
        try {
            enumValue = Enum.valueOf(enumClass, name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceRuntimeException(ErrorCodeEnum.VALIDATION_ERROR, new Throwable());
        } catch (NullPointerException e) {
            enumValue = null;
        }
        return enumValue;
    }
}
