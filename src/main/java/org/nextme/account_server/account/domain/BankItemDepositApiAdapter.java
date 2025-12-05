package org.nextme.account_server.account.domain;


import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;

import java.util.List;

public interface BankItemDepositApiAdapter {
    // 금융상품 정보 반환
    List<BankItemResponse> getBankItemDepositList(String financeCd);
}
