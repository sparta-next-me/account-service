package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Builder
public record AccountDeleteRequest(
        //@NotNull List<AccountList> accountList, // 사용자 계정 정보 리스트
        @NotNull String connectedId, // 커넥티드 아이디
        @NotNull UUID accountId,//계좌 아이디
        UUID userId
) {

}
