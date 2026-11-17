# JWT Token Validation and Claims Extraction Using Spring Boot

- **Project Title:** JWT Token Validation and Claims Extraction Service
- **Student Name:** GARLAPATI YASWANTH ADITHYA REDDY
- **Student ID:** 2400030358
- **Repository:** https://github.com/aadi30358/2400030358-SOAEndSemSkill.git

---

## Execution Screenshots

### 1. Valid Token Validation (200 OK)

Valid token generated and claims verified with `POST /jwt/validate` :

![Valid Token](screenshots/1_valid_token.png)

### 2. Altered Token Validation (401 Invalid Signature)

Token signature altered by modifying the signature character and verified with `POST /jwt/validate` :

![Altered Token](screenshots/2_altered_token.png)

### 3. Expired Token Validation (401 Token Expired)

Expired token generated with negative expiration (`expirationMs: -1000`) and verified with `POST /jwt/validate` :

![Expired Token](screenshots/3_expired_token.png)
