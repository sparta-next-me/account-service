package org.nextme.account_server.account.domain;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountDeleteRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.CodefDeleteAccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountDeleteResponse;

import java.util.List;

public interface AccountDeleteApiAdapter {

    List<AccountDeleteResponse> deleteAccount(CodefDeleteAccountRequest codefDeleteAccountRequest,AccountDeleteRequest request);
}
