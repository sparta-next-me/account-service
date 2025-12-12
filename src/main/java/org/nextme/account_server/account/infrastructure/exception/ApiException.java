package org.nextme.account_server.account.infrastructure.exception;

import org.nextme.account_server.global.infrastructure.exception.ApplicationException;

public class ApiException extends ApplicationException {
    public ApiException(ApiErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public ApiException(ApiErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), message);
    }
}
