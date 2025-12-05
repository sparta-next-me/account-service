package org.nextme.account_server.account.application.bankItem.exception;

import org.nextme.account_server.account.application.bank.exception.BankErrorCode;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;

public class BankItemException extends ApplicationException {
    public BankItemException(BankItemErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public BankItemException(BankItemErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), message);
    }
}
