package com.sqy.domain.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    private long id;
    private String email;
    private String password;
}
