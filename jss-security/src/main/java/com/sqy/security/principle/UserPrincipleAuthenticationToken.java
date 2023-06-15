package com.sqy.security.principle;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serial;

public class UserPrincipleAuthenticationToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 3259196531551603860L;
    private final UserPrinciple userPrinciple;

    public UserPrincipleAuthenticationToken(UserPrinciple userPrinciple) {
        super(userPrinciple.getAuthorities());
        this.userPrinciple = userPrinciple;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrinciple getPrincipal() {
        return userPrinciple;
    }
}
