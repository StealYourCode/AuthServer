package be.integration.api.controllers;

import be.integration.api.dtos.LoginRequest;
import be.integration.api.dtos.RegisterRequest;
import be.integration.models.entities.User;
import be.integration.repository.HashMapIUserRepository;
import be.integration.repository.IUserRepository;
import be.integration.services.CertServices;
import be.integration.services.TokenServices;
import be.integration.services.UserServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/amazi/authserver/v1")
public class AuthController {

    private final UserServices userServices;

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    // Accept any implementation of UserRepository through the constructor
    public AuthController(IUserRepository IUserRepository) throws Exception {
        CertServices certServices = new CertServices();
        TokenServices tokenService = new TokenServices(certServices);

        this.userServices = new UserServices(IUserRepository, tokenService);
    }

    // DEFAULT CONSTRUCTOR for now (manual injection with HashMap repo)
    public AuthController() throws Exception {
        this(new HashMapIUserRepository());
    }

    @PostMapping("/users/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest request) {
        logger.info("API called: Register user '{}'", request.getUsername());

        User createdUser = userServices.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );

        if (createdUser != null) {
            logger.info("User '{}' registered successfully", createdUser.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully.");
            response.put("clientId", createdUser.getId().substring(0, 8)); // Assuming 8 chars partial clientId
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            logger.info("User registration failed for '{}'", request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Registration failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/auth/loginBasic")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest request) {
        logger.info("API called: Login attempt for identifier '{}'", request.getIdentifier());

        String token = userServices.loginUserByEmailOrClientId(
                request.getIdentifier(),
                request.getPassword()
        );

        if (token != null) {
            logger.info("Login successful for identifier '{}'", request.getIdentifier());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful.");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            logger.warn("Login failed for identifier '{}'", request.getIdentifier());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials (email or clientId, and password).");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
