package com.sqy.domain.login;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LoginResponse {
    private final String accessToken;
}
