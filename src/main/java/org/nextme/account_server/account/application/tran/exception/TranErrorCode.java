package org.nextme.account_server.account.application.tran.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TranErrorCode {
    TRAN_MISSING_PARAMETER(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "필수 요청 파라미터가 없습니다"),
    DUPLICATE_TRAN(HttpStatus.CONFLICT, "CONFLICT", "이미 존재하는 거래내역입니다."),
    NOT_FOUND_ID(HttpStatus.NOT_FOUND,"NOT_FOUND", "계좌 아이디가 존재하지 않습니다."),
    NOT_FOUND_TRAN(HttpStatus.NOT_FOUND,"NOT_FOUND", "거래내역이 존재하지 않습니다."),
    NOT_FOUND_USER_ID(HttpStatus.NOT_FOUND,"NOT_FOUND", "계좌의 사용자 아이디가 존재하지 않습니다."),
    TRAN_ERROR_CODE(HttpStatus.BAD_REQUEST,"BAD_REQUEST","정상코드가 아닙니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;

    TranErrorCode(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.defaultMessage = message;
    }
}
