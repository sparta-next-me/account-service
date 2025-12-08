package org.nextme.account_server.account.domain;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountDeleteRequest;

public interface AccountDeleteApiAdapter {

    void deleteAccount(AccountDeleteRequest request);
}
