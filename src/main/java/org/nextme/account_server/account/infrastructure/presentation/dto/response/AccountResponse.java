package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.nextme.account_server.account.domain.entiry.Account;

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
