package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class WeakPasswordException extends ApiException {
    public WeakPasswordException() {
        super(ErrorCode.WEAK_PASSWORD);
    }
}

