# 2400030358-SOAEndSemSkill: JWT Validation Service

## Student Details
- **Student ID:** 2400030358
- **Project Name:** 2400030358-SOAEndSemSkill
- **Artifact:** jwtvalidator
- **Base Package:** com.klu.jwtvalidator

---

## Tech Stack
- **Language & Runtime:** Java 17
- **Framework:** Spring Boot 3.3.5
- **Build Tool:** Maven
- **Security & Tokens:** JJWT (Java JWT) 0.12.6 (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
- **API Documentation & Testing:** SpringDoc OpenAPI 2.6.0 (`springdoc-openapi-starter-webmvc-ui`)
- **Utilities:** Project Lombok, Spring Boot Starter Validation
- **Testing:** Spring Boot Starter Test (JUnit 5, MockMvc)

---

## Configuration (`application.properties`)
```properties
server.port=8080
jwt.secret=lhDWTZAqD5WIdk+m6ForEtIE3B2tVUM8IMZ7vtlSRlQ=
jwt.expiration-ms=3600000
springdoc.swagger-ui.path=/swagger-ui.html
```

---

## How to Run

### 1. Build and Run Tests
```bash
mvn clean test
```

### 2. Start the Application
```bash
mvn spring-boot:run
```

### 3. Open Swagger UI
Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in your browser.

---

## Endpoints

| HTTP Method | Path | Description | Request Body | Success Response | Error Response |
|-------------|------|-------------|--------------|------------------|----------------|
| `POST` | `/jwt/generate` | Generates a signed HS256 JWT token with claims for testing | `GenerateTokenRequest` | `200 OK` (token string) | `400 Bad Request` |
| `POST` | `/jwt/validate` | Validates token signature & expiration, extracting claims | `TokenRequest` | `200 OK` (`TokenValidationResponse`) | `400 / 401 Unauthorized` |

---

## Sample Requests & Responses

### 1. Generate Token (POST `/jwt/generate`)
#### Request:
```json
{
  "username": "aadi",
  "roles": [
    "USER",
    "ADMIN"
  ],
  "expirationMs": 3600000
}
```
#### Response (`200 OK`):
```text
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWRpIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl0sImlhdCI6MTc5NDkyMzI1MCwiZXhwIjoxNzk0OTI2ODUwfQ.KiEhR9R3wvp71NKNd7O0YBw08r-qXlbkdqjkZk0GO84
```

---

### 2. Validate Valid Token (POST `/jwt/validate`)
#### Request:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWRpIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl0sImlhdCI6MTc5NDkyMzI1MCwiZXhwIjoxNzk0OTI2ODUwfQ.KiEhR9R3wvp71NKNd7O0YBw08r-qXlbkdqjkZk0GO84"
}
```
#### Response (`200 OK`):
```json
{
  "valid": true,
  "username": "aadi",
  "roles": [
    "USER",
    "ADMIN"
  ],
  "issuedAt": "2026-11-17T13:47:30.000+00:00",
  "expiresAt": "2026-11-17T14:47:30.000+00:00",
  "message": "Token is valid"
}
```

---

### 3. Validate Altered Token (POST `/jwt/validate`)
*(Altering a single character in the signature)*
#### Request:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWRpIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl0sImlhdCI6MTc5NDkyMzI1MCwiZXhwIjoxNzk0OTI2ODUwfQ.KiEhR9R3wvp71NKNd7O0YBw08r-qXlbkdqjkZk0GO8a"
}
```
#### Response (`401 Unauthorized`):
```json
{
  "valid": false,
  "username": null,
  "roles": null,
  "issuedAt": null,
  "expiresAt": null,
  "message": "Invalid token signature"
}
```

---

### 4. Validate Expired Token (POST `/jwt/validate`)
*(Token generated with negative expiration `expirationMs: -1000`)*
#### Request:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWRpIiwicm9sZXMiOlsiVVNFUiIsIkFETUlOIl0sImlhdCI6MTc5NDkyMzI1MCwiZXhwIjoxNzk0OTIzMjQ5fQ.lGMnILsDx1NVtBLSHj4_rXJ7OMtSDfzVs3t65h2nEpU"
}
```
#### Response (`401 Unauthorized`):
```json
{
  "valid": false,
  "username": null,
  "roles": null,
  "issuedAt": null,
  "expiresAt": null,
  "message": "Token has expired"
}
```

---

### 5. Validate Empty Token (POST `/jwt/validate`)
#### Request:
```json
{
  "token": ""
}
```
#### Response (`400 Bad Request`):
```json
{
  "valid": false,
  "username": null,
  "roles": null,
  "issuedAt": null,
  "expiresAt": null,
  "message": "Token must not be empty"
}
```

---

## Screenshots

### 1. Valid Token Validation (200 OK)
![Valid Token](screenshots/1_valid_token.png)

### 2. Altered Token Validation (401 Invalid Signature)
![Altered Token](screenshots/2_altered_token.png)

### 3. Expired Token Validation (401 Token Expired)
![Expired Token](screenshots/3_expired_token.png)
