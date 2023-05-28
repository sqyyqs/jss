package com.sqy.controller;

import com.sqy.domain.login.LoginRequest;
import com.sqy.domain.login.LoginResponse;
import com.sqy.security.jwt.JwtIssuer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtIssuer jwtIssuer;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        log.info("Invoke login({}).", loginRequest);
        String token = jwtIssuer.issue(loginRequest.getId(), loginRequest.getEmail(), List.of("USER"));
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
