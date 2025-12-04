package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entiry.Account;

import java.time.LocalDateTime;

@Builder
public record AccountSelectResponse(
        String bankAccount, // 계좌번호
        LocalDateTime createdAt, // 연동일자
        String bankName, // 은행이름
        boolean locked // 계정상태

){
    public static AccountSelectResponse of(Account account) {
        return AccountSelectResponse.builder()
                .bankAccount(account.getBankAccount())
                .createdAt(account.getCreatedAt())
//                .bankName(account.g())
                .locked(account.isLocked())
                .build();
    }
}
