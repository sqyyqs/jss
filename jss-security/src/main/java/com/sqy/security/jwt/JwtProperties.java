package com.sqy.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProperties {

    @Value("secret-key")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}
