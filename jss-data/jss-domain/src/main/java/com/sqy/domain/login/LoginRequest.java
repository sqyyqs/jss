package com.sqy.domain.login;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LoginRequest {
    private long id;
    private String email;
    private String password;
}
