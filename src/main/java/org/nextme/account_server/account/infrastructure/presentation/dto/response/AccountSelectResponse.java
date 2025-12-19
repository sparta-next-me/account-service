package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AccountSelectResponse(
        UUID accountId, // 계좌 아이디
        String connectedId, // 커넥티드 아이디
        String bankAccount, // 계좌번호
        LocalDateTime createdAt, // 연동일자
        String bankName, // 은행이름
        boolean locked // 계정상태

){
    public static AccountSelectResponse of(Account account) {
        return AccountSelectResponse.builder()
                .accountId(account.getId().getId())
                .connectedId(account.getClientId())
                .bankAccount(account.getBankAccount())
                .createdAt(account.getCreatedAt())
                .bankName(account.getBank().getBankName())
                .locked(account.isLocked())
                .build();
    }
}
