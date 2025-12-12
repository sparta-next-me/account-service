package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import java.util.UUID;

public record TranSelectRequest(
        UUID userId, // 유저ID
        String tranDate, // 거래일자
        String tranTime, // 거래시각
        int deposit, // 출금
        int withdraw // 입금

) {
}
