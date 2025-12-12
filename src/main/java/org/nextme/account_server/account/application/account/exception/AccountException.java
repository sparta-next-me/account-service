package org.nextme.account_server.account.application.account.exception;

import org.nextme.account_server.global.infrastructure.exception.ApplicationException;

public class AccountException extends ApplicationException {
    public AccountException(AccountErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public AccountException(AccountErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), message);
    }
}
