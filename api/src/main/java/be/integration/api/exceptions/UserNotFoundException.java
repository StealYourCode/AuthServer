package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
