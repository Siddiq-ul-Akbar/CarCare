package com.carcare.auth_service.dto;

public class AuthResponse {
    private String token;

    // Constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Empty constructor (if needed by frameworks)
    public AuthResponse() {
    }

    // --- Getter and Setter ---

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
