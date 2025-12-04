package org.nextme.account_server.account.infrastructure.presentation.dto.request;

public record AccountRequest(
        String organization, // 기관코드(은행코드)
        String connectedId, // 인증이 필요한 API 요청을 위한 키값
        String userName // db에 저장할 계좌 주인 이름(api에서 알려주지 않음)
){

}
