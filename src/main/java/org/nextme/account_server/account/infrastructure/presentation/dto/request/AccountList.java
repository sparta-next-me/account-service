package org.nextme.account_server.account.infrastructure.presentation.dto.request;

public record AccountList(
        String countryCode, //국가코드
        String businessType, //업무 구분
        String organization, // 기관코드
        String clientType, // 고객구분(개인 : P / 기업, 법인 : B / 통합 : A)
        String loginType // 로그인 방식(0 : 인증서, 1 : 아이디/패스워드)
) {

}
