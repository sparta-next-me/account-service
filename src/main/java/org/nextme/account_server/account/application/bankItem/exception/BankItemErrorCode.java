package org.nextme.account_server.account.application.bankItem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BankItemErrorCode {

    DUPLICATE_BANK_ITEM(HttpStatus.CONFLICT, "CONFLICT", "이미 존재하는 코드입니다."),
    BANK_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "금융상품을 찾을 수 없습니다."),
    BANK_ITEM_MISSING_PARAMETER(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "필수 요청 파라미터가 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;

    BankItemErrorCode(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.defaultMessage = message;
    }
}
