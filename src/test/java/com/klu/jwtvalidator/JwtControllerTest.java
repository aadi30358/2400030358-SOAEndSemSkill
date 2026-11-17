package com.klu.jwtvalidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klu.jwtvalidator.dto.TokenRequest;
import com.klu.jwtvalidator.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("Valid token returns 200 with username and roles")
    void testValidToken() throws Exception {
        String token = jwtUtil.generateToken("aadi", List.of("USER", "ADMIN"), 3600000);
        TokenRequest request = new TokenRequest(token);

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.username").value("aadi"))
                .andExpect(jsonPath("$.roles", containsInAnyOrder("USER", "ADMIN")))
                .andExpect(jsonPath("$.message").value("Token is valid"));
    }

    @Test
    @DisplayName("Altered token signature returns 401 Invalid token signature")
    void testAlteredToken() throws Exception {
        String token = jwtUtil.generateToken("aadi", List.of("USER"), 3600000);
        char lastChar = token.charAt(token.length() - 1);
        char alteredChar = (lastChar == 'a') ? 'b' : 'a';
        String tamperedToken = token.substring(0, token.length() - 1) + alteredChar;

        TokenRequest request = new TokenRequest(tamperedToken);

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.message").value("Invalid token signature"));
    }

    @Test
    @DisplayName("Expired token returns 401 Token has expired")
    void testExpiredToken() throws Exception {
        String expiredToken = jwtUtil.generateToken("aadi", List.of("USER"), -1000);
        TokenRequest request = new TokenRequest(expiredToken);

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.message").value("Token has expired"));
    }

    @Test
    @DisplayName("Empty token returns 400 Token must not be empty")
    void testEmptyToken() throws Exception {
        TokenRequest request = new TokenRequest("");

        mockMvc.perform(post("/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.message").value("Token must not be empty"));
    }
}
