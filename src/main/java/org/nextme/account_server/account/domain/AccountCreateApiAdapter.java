package org.nextme.account_server.account.domain;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountCreateRequest;

public interface AccountCreateApiAdapter {
    // 커넥티드 아이디 발급
    String getConnectedId(AccountCreateRequest request);
}
