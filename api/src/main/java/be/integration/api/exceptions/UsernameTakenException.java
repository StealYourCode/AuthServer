package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class UsernameTakenException extends ApiException {
    public UsernameTakenException() {
        super(ErrorCode.USERNAME_TAKEN);
    }
}

