package org.nextme.account_server.account.infrastructure.presentation.dto.request;

public record AccountCreateRequest(
        String organization, // 은행코드
        String id// 실제 은행 아이디
) {
}
