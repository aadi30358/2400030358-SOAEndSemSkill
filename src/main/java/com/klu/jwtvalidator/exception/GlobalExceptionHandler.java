package com.klu.jwtvalidator.exception;

import com.klu.jwtvalidator.dto.TokenValidationResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<TokenValidationResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Token has expired")
                        .build());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<TokenValidationResponse> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Invalid token signature")
                        .build());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<TokenValidationResponse> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Malformed token")
                        .build());
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<TokenValidationResponse> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Invalid token")
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TokenValidationResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Invalid token")
                        .build());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<TokenValidationResponse> handleOtherJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Invalid token")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TokenValidationResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(TokenValidationResponse.builder()
                        .valid(false)
                        .message("Token must not be empty")
                        .build());
    }
}
