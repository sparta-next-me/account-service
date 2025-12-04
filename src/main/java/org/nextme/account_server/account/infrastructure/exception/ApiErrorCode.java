package org.nextme.account_server.account.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {

    API_RESPONSE_EMPTY(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "응답값이 없습니다."),
    API_REQUEST_INVALID (HttpStatus.BAD_REQUEST, "BAD_REQUEST", "외부 API 호출 실패했습니다."),
    API_MISSING_PARAMETER(HttpStatus.BAD_REQUEST,"BAD_REQUEST", "필수 요청 파라미터가 없습니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;

    ApiErrorCode(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.defaultMessage = message;
    }
}
