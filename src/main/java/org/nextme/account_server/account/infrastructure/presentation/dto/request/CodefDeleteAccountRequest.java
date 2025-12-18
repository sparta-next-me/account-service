package org.nextme.account_server.account.infrastructure.presentation.dto.request;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CodefDeleteAccountRequest {
    String countryCode = "KR"; // 나라 코드
    String businessType = "BK"; // 은행, 저축은행 : BK, 카드 : CD , 증권 : ST ,보험 : IS
    String clientType = "P"; // 고객 구분 -> 개인 : P / 기업, 법인 : B / 통합 : A
    String organization; // 은행코드
    String loginType = "1"; // 로그인 타입 -> 0 : 인증서, 1 : 아이디/패스워드

    public CodefDeleteAccountRequest( String organization) {
        this.organization = organization;
    }
}
