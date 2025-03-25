package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}

