package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import java.util.UUID;

public record AccountSelectRequest(
        UUID accountId, // 계좌ID
        String connectdId, // 커넥티드아이디
        UUID userId // 유저ID
) {

}
