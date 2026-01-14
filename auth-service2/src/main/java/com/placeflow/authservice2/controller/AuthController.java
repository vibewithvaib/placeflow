package com.placeflow.authservice2.controller;

import com.placeflow.authservice2.dto.LoginRequest;
import com.placeflow.authservice2.dto.LoginResponse;
import com.placeflow.authservice2.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );
        return new LoginResponse(token);
    }
}