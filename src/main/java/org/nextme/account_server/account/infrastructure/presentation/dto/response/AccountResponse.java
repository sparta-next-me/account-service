package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entity.Account;

import java.util.UUID;

@Builder

public record AccountResponse(
        UUID userId,
        UUID accountId,
        String userName,
        String accountNumber,
        String clientId
) {
    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .accountId(account.getId().getId())
                .userId(account.getUserId())
                .userName(account.getUserName())
                .clientId(account.getClientId())
                .accountNumber(account.getBankAccount())
                .build();
    }
}
