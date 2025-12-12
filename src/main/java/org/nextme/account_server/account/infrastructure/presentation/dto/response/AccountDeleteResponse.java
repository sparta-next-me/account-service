package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import java.util.List;

public record AccountDeleteResponse(
        String connectedId, //커넥티트 아이디
        List<AccountDeleteSuccessList> successList, // 성공 리스트
        List<AccountDeleteErrorList > errorList // 오류리스트

) {
}
