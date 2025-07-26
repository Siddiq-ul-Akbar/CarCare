package com.carcare.vehicle_service.dto;

import lombok.Data;

@Data
public class VehicleRequest {
    private String vin;
    private String model;
    private String color;
    private int year;
}
