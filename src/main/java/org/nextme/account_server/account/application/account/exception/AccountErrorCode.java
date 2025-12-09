package org.nextme.account_server.account.application.account.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AccountErrorCode {

    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "계좌번호를 찾을 수 없습니다."),
    ACCOUNT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "계좌아이디를 찾을 수 없습니다."),
    DUPLICATE_ACCOUNT(HttpStatus.CONFLICT, "CONFLICT", "이미 존재하는 계좌입니다."),
    ACCOUNT_VALUE_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "UNPROCESSABLE_ENTITY", "값이 일치하지 않습니다;");

    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;

    AccountErrorCode(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.defaultMessage = message;
    }
}
