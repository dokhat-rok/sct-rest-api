package com.sct.rest.api.util;

public class EnumConverter {

    public static <E extends Enum<E>> E stringToEnum(Class<E> enumClass, String name) {
        E enumValue;
        try {
            enumValue = Enum.valueOf(enumClass, name.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            enumValue = null;
        }
        return enumValue;
    }
}
