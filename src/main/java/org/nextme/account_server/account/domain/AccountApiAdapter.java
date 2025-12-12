package org.nextme.account_server.account.domain;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;

import java.util.List;

public interface AccountApiAdapter {


    // 계좌 정보 반환
    String getAccount(AccountRequest request);

}
