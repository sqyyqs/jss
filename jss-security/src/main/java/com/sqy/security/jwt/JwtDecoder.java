package com.sqy.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    public JwtDecoder(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build()
                .verify(token);
    }
}
