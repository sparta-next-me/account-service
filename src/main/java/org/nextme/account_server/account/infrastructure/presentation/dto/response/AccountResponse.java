package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entity.Account;

@Builder

public record AccountResponse(
        String userName,
        String accountNumber,
        String clientId
) {
    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .userName(account.getUserName())
                .clientId(account.getClientId())
                .accountNumber(account.getBankAccount())
                .build();
    }
}
