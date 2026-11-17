package com.klu.jwtvalidator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTokenRequest {

    @Schema(description = "Username for the token subject", example = "aadi")
    private String username;

    @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\"]")
    private List<String> roles;

    @Schema(description = "Expiration time in milliseconds (optional, negative value creates an expired token)", example = "3600000")
    private Long expirationMs;
}
