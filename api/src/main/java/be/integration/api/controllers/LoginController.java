package be.integration.api.controllers;

import be.integration.api.dtos.LoginRequest;
import be.integration.api.responses.ApiResponse;
import be.integration.api.responses.ErrorCode;
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
public class LoginController {

    private final UserServices userServices;
    private static final Logger Logger = LogManager.getLogger(LoginController.class);

    public LoginController() {
        // ✅ MANUAL DEPENDENCY INJECTION
        IUserRepository userRepository = new HashMapIUserRepository();
        CertServices certServices = new CertServices();
        TokenServices tokenService = new TokenServices(certServices);

        this.userServices = new UserServices(userRepository, tokenService);
    }

    @PostMapping("/amazi/authserver/v1/auth/loginBasic")
    public ResponseEntity<ApiResponse<Map<String, String>>> loginUser(@RequestBody LoginRequest request) {
        String token = userServices.loginUserByEmailOrClientId(request.getIdentifier(), request.getPassword());

        if (token == null) {
            Logger.info("API called:  INVALID CREDENTIALS'{}'" + request.getIdentifier());
            return ApiResponse.error(ErrorCode.INVALID_CREDENTIALS.getMessage(), ErrorCode.INVALID_CREDENTIALS.getStatus());
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful.");
        response.put("token", token);
        Logger.info("API called:  LOG IN SUCCESS'{}'" + request.getIdentifier());

        return ApiResponse.success("Login successful.", response, HttpStatus.OK);
    }
}
