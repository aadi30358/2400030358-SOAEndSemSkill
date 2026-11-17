package com.klu.jwtvalidator.service;

import com.klu.jwtvalidator.dto.GenerateTokenRequest;
import com.klu.jwtvalidator.dto.TokenValidationResponse;
import com.klu.jwtvalidator.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public TokenValidationResponse validate(String token) {
        Claims claims = jwtUtil.parseToken(token);
        return TokenValidationResponse.builder()
                .valid(true)
                .username(jwtUtil.extractUsername(claims))
                .roles(jwtUtil.extractRoles(claims))
                .issuedAt(claims.getIssuedAt())
                .expiresAt(claims.getExpiration())
                .message("Token is valid")
                .build();
    }

    public String generate(GenerateTokenRequest request) {
        long expiration = (request.getExpirationMs() != null)
                ? request.getExpirationMs()
                : jwtUtil.getDefaultExpirationMs();
        return jwtUtil.generateToken(request.getUsername(), request.getRoles(), expiration);
    }
}
