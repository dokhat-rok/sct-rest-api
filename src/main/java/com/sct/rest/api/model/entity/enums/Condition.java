package com.sct.rest.api.model.entity.enums;

public enum Condition {
    EXCELLENT, GOOD, SATISFACTORY;

    public static Condition getCondition(String cond){
        return switch (cond) {
            case "EXCELLENT" -> Condition.EXCELLENT;
            case "GOOD" -> Condition.GOOD;
            case "SATISFACTORY" -> Condition.SATISFACTORY;
            default -> null;
        };
    }
}
