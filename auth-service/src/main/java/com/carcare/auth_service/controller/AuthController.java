package com.carcare.auth_service.controller;

import com.carcare.auth_service.dto.AuthRequest;
import com.carcare.auth_service.dto.AuthResponse;
import com.carcare.auth_service.dto.RegisterRequest;
import com.carcare.auth_service.dto.UserDto;
import com.carcare.auth_service.model.User;
import com.carcare.auth_service.repository.UserRepository;
import com.carcare.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepo;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
