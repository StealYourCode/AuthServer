package be.integration.api.exceptions;

import be.integration.api.responses.ErrorCode;

public class InternalErrorException extends ApiException {
    public InternalErrorException() {
        super(ErrorCode.INTERNAL_ERROR);
    }
}
