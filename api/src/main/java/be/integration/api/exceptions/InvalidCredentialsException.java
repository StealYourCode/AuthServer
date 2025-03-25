package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}

