package com.sqy.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JwtToPrincipleMapper {
    public UserPrinciple convert(DecodedJWT jwt) {
        return new UserPrinciple(Long.valueOf(
                jwt.getSubject()),
                jwt.getClaim("email").toString(),
                extreactAuthoritiesFromClaim(jwt)
        );
    }

    private List<SimpleGrantedAuthority> extreactAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim roles = jwt.getClaim("roles");
        if (roles.isNull() || roles.isMissing()) {
            return Collections.emptyList();
        }
        return roles.asList(SimpleGrantedAuthority.class);
    }
}
