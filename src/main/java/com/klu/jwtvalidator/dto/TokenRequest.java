package com.klu.jwtvalidator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

    @NotBlank(message = "Token must not be empty")
    @Schema(description = "JWT Token string (with or without 'Bearer ' prefix)", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
}
