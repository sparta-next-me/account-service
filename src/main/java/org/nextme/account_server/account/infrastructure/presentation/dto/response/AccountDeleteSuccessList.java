package org.nextme.account_server.account.infrastructure.presentation.dto.response;

public record AccountDeleteSuccessList(
        String code, // codef에서 반환하는 결과코드
        String message, // 메세지
        String countryCode, // 국가코드
        String clientType, //고객 구분(개인 : P / 기업, 법인 : B / 통합 : A)
        String organization, // 기관코드
        String businessType //업무 구분(은행, 저축은행 : BK, 카드 : CD , 증권 : ST ,보험 : IS)
) {
}
