package com.carcare.vehicle_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponse {
    private Long id;
    private String vin;
    private String model;
    private String color;
    private int year;
}
