package com.klu.jwtvalidator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {

    @Schema(description = "Indicates whether the token is valid", example = "true")
    private boolean valid;

    @Schema(description = "Subject / Username extracted from token", example = "aadi")
    private String username;

    @Schema(description = "List of roles extracted from token claims", example = "[\"USER\", \"ADMIN\"]")
    private List<String> roles;

    @Schema(description = "Token issuance timestamp")
    private Date issuedAt;

    @Schema(description = "Token expiration timestamp")
    private Date expiresAt;

    @Schema(description = "Status or error message", example = "Token is valid")
    private String message;
}
