package com.carcare.vehicle_service.client;

import com.carcare.vehicle_service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;

    public Optional<UserDto> getUserById(Long userId) {
        try {
            ResponseEntity<UserDto> response = restTemplate.getForEntity(
                    "http://localhost:8081/api/auth/" + userId,
                    UserDto.class
            );
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return Optional.empty();
        }
    }
}