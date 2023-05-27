package com.sqy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipleMapper jwtToPrincipleMapper;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtToPrincipleMapper jwtToPrincipleMapper) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipleMapper = jwtToPrincipleMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipleMapper::convert)
                .map(UserPrincipleAuthenticationToken::new)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return Optional.of(authorization.substring(7));
        }
        return Optional.empty();
    }
}
