package com.sct.rest.api.model.entity;

public enum ParkingType {
    ALL, ONLY_BICYCLE, ONLY_SCOOTER;

    public ParkingType getParkingType(String type){
        return switch (type) {
            case "ALL" -> ParkingType.ALL;
            case "ONLY_BICYCLE" -> ParkingType.ONLY_BICYCLE;
            case "ONLY_SCOOTER" -> ParkingType.ONLY_SCOOTER;
            default -> null;
        };
    }
}