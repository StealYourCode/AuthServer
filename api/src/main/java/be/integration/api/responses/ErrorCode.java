package be.integration.api.responses;

import org.springframework.http.HttpStatus;

// HttpStatus.BAD_REQUEST  // 400
// HttpStatus.UNAUTHORIZED // 401
// HttpStatus.FORBIDDEN    // 403
// HttpStatus.NOT_FOUND    // 404
// HttpStatus.INTERNAL_SERVER_ERROR // 500


public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials."),
    USERNAME_TAKEN(HttpStatus.BAD_REQUEST, "Username already exists."),
    WEAK_PASSWORD(HttpStatus.BAD_REQUEST, "Password is too weak."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username already exists."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

