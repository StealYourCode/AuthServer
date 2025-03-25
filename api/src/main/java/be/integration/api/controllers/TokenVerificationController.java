package be.integration.api.controllers;

import be.integration.services.CertServices;
import be.integration.services.TokenServices;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/amazi/authserver/v1/auth")
public class TokenVerificationController {

    private final TokenServices tokenServices;
    private final CertServices certServices;

    public TokenVerificationController() throws Exception {
        this.certServices = new CertServices();  // ✅ Initialize it first
        this.tokenServices = new TokenServices(certServices);
    }


    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        Map<String, Object> response = new HashMap<>();

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.put("status", "error");
            response.put("message", "Missing or malformed Authorization header.");
            response.put("errorCode", "INVALID_AUTH_HEADER");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        DecodedJWT decodedJWT = tokenServices.verifyToken(token);

        if (decodedJWT == null) {
            response.put("status", "error");
            response.put("message", "Invalid or expired token.");
            response.put("errorCode", "INVALID_TOKEN");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("status", "success");
        response.put("message", "Token is valid.");
        response.put("data", Map.of(
                "userId", decodedJWT.getSubject(),
                "issuedAt", decodedJWT.getIssuedAt(),
                "expiresAt", decodedJWT.getExpiresAt()
        ));

        return ResponseEntity.ok(response);
    }
}

