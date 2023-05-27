package com.sqy.controller;

import com.sqy.domain.login.LoginRequest;
import com.sqy.domain.login.LoginResponse;
import com.sqy.security.jwt.JwtIssuer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtIssuer jwtIssuer;

    public AuthController(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String token = jwtIssuer.issue(loginRequest.getId(), loginRequest.getEmail(), List.of("USER"));
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
