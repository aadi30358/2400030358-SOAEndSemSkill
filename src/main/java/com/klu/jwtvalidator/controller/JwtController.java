package com.klu.jwtvalidator.controller;

import com.klu.jwtvalidator.dto.GenerateTokenRequest;
import com.klu.jwtvalidator.dto.TokenRequest;
import com.klu.jwtvalidator.dto.TokenValidationResponse;
import com.klu.jwtvalidator.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@Tag(name = "JWT", description = "Operations for generating, validating and parsing JWT tokens")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/validate")
    @Operation(
            summary = "Validate JWT Token",
            description = "Validates the provided JWT token signature and expiration, extracting username and roles claims.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Token to validate",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TokenRequest.class),
                            examples = @ExampleObject(
                                    name = "Sample Token Request",
                                    value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWRpIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl19...\"}"
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Token is valid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenValidationResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"valid\": true, \"username\": \"aadi\", \"roles\": [\"USER\", \"ADMIN\"], \"issuedAt\": \"2026-11-17T13:30:00.000+00:00\", \"expiresAt\": \"2026-11-17T14:30:00.000+00:00\", \"message\": \"Token is valid\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request (e.g. Empty or blank token)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenValidationResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"valid\": false, \"message\": \"Token must not be empty\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized (Invalid signature, expired, or malformed token)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenValidationResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"valid\": false, \"message\": \"Invalid token signature\"}"
                            )
                    )
            )
    })
    public ResponseEntity<TokenValidationResponse> validateToken(@Valid @RequestBody TokenRequest request) {
        TokenValidationResponse response = jwtService.validate(request.getToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate")
    @Operation(
            summary = "Generate JWT Token",
            description = "Generates a signed JWT token with custom username, roles, and expiration for testing.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details for generating token",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = GenerateTokenRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Token Request",
                                            value = "{\"username\": \"aadi\", \"roles\": [\"USER\", \"ADMIN\"], \"expirationMs\": 3600000}"
                                    ),
                                    @ExampleObject(
                                            name = "Expired Token Request",
                                            value = "{\"username\": \"aadi\", \"roles\": [\"USER\", \"ADMIN\"], \"expirationMs\": -1000}"
                                    )
                            }
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Token generated successfully",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid generation parameters",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenValidationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenValidationResponse.class))
            )
    })
    public ResponseEntity<String> generateToken(@RequestBody GenerateTokenRequest request) {
        String token = jwtService.generate(request);
        return ResponseEntity.ok(token);
    }
}
