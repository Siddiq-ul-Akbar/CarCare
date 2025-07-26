package com.carcare.auth_service.service;

import com.carcare.auth_service.config.JwtService;
import com.carcare.auth_service.dto.AuthRequest;
import com.carcare.auth_service.dto.AuthResponse;
import com.carcare.auth_service.dto.RegisterRequest;
import com.carcare.auth_service.exception.EmailAlreadyRegisteredException;
import com.carcare.auth_service.model.Role;
import com.carcare.auth_service.model.User;
import com.carcare.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepo.save(user);

        String jwt = jwtService.generateToken(toUserDetails(user));
        return new AuthResponse(jwt);
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            logger.warn("Authentication failed: {}", e.getMessage());
            throw e;
        }

        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(toUserDetails(user));
        return new AuthResponse(jwt);
    }

    private UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}