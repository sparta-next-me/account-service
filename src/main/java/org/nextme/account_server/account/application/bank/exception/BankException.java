package org.nextme.account_server.account.application.bank.exception;

import org.nextme.account_server.global.infrastructure.exception.ApplicationException;

public class BankException extends ApplicationException {
    public BankException(BankErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public BankException(BankErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), message);
    }
}
