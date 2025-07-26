package com.carcare.vehicle_service.controller;

import com.carcare.vehicle_service.dto.VehicleRequest;
import com.carcare.vehicle_service.dto.VehicleResponse;
import com.carcare.vehicle_service.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(
            @RequestBody VehicleRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(service.addVehicle(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehicles(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(service.getAllByUser(userId));
    }
}
