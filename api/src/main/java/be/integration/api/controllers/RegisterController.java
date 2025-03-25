package be.integration.api.controllers;

import be.integration.api.dtos.RegisterRequest;
import be.integration.api.responses.ApiResponse;
import be.integration.api.responses.ErrorCode;
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

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    private final UserServices userServices;

    // LOGGER FOR LOGS OF ACCOUNT CREATION
    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    public RegisterController() throws Exception {
        IUserRepository userRepository = new HashMapIUserRepository(); // NEED TO CHANGE TO DB INTERFACE LATER ON
        CertServices certServices = new CertServices();
        TokenServices tokenService = new TokenServices(certServices);

        this.userServices = new UserServices(userRepository, tokenService);
    }

    @PostMapping(value = "/amazi/authserver/v1/register/users", produces = "application/json")
    public ResponseEntity<ApiResponse<Map<String, String>>> registerUser(@RequestBody RegisterRequest request) {
        User createdUser = userServices.registerUser(
                request.getPassword(),
                request.getEmail()
        );

        if (createdUser == null) {
            logger.info("API called: EMAIL ALREADY IN DATABASE '{}'" + request.getEmail());
            return ApiResponse.error(ErrorCode.USER_ALREADY_EXISTS.getMessage(), ErrorCode.USER_ALREADY_EXISTS.getStatus());
        }

        Map<String, String> response = new HashMap<>();
        logger.info("API called: Register user '{}'" + request.getEmail());
        response.put("message", "User registered successfully.");
        response.put("clientId", createdUser.getId().substring(0, 8)); // Get the First 8 characters of the clientID

        return ApiResponse.success( "User registered successfully.", response, HttpStatus.CREATED);
    }
}
