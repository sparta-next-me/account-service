package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record AccountDeleteRequest(
        List<AccountList> accountList, // 사용자 계정 정보 리스트
        String connectedId, // 커넥티드 아이디
        UUID accountId, //계좌 아이디
        UUID userId // 유저 아이디
) {
}
