package com.edurise.EduRise.controller;

import com.edurise.EduRise.dto.AuthResponse;
import com.edurise.EduRise.dto.LoginRequest;
import com.edurise.EduRise.dto.RegisterRequest;
import com.edurise.EduRise.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin (origins="http://localhost:3000")// Allow cross-origin requests (adjust origins as needed)
public class AuthController {

    private final AuthService authService;

    // Constructor injection of AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
