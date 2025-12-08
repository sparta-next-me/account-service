package org.nextme.account_server.account.domain;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;

public interface ApiAdapter {


    String getAccount(AccountRequest request);
}
