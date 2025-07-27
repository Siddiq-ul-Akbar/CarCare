package com.carcare.vehicle_service.service;

import com.carcare.vehicle_service.client.AuthClient;
import com.carcare.vehicle_service.dto.UserDto;
import com.carcare.vehicle_service.dto.VehicleRequest;
import com.carcare.vehicle_service.dto.VehicleResponse;
import com.carcare.vehicle_service.model.Vehicle;
import com.carcare.vehicle_service.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repo;
    @Autowired
    private AuthClient authClient;
    public VehicleResponse addVehicle(VehicleRequest request, Long userId) {
        Optional<UserDto> user = authClient.getUserById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("Invalid user. Cannot add vehicle.");
        }
        Vehicle v = new Vehicle();
        v.setVin(request.getVin());
        v.setModel(request.getModel());
        v.setColor(request.getColor());
        v.setYear(request.getYear());
        v.setUserId(userId);

        repo.save(v);
        return new VehicleResponse(v.getId(), v.getVin(), v.getModel(), v.getColor(), v.getYear());
    }

    public List<VehicleResponse> getAllByUser(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(v -> new VehicleResponse(v.getId(), v.getVin(), v.getModel(), v.getColor(), v.getYear()))
                .toList();
    }
}
