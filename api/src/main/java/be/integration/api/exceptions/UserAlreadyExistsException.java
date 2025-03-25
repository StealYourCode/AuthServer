package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
