package org.nextme.account_server.account.application.tran.exception;

import org.nextme.account_server.global.infrastructure.exception.ApplicationException;

public class TranException extends ApplicationException {
    public TranException(TranErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public TranException(TranErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), errorCode.getCode(), message);
    }
}
