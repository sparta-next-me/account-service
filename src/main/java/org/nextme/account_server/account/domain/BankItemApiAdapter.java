package org.nextme.account_server.account.domain;


import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;

import java.util.List;

public interface BankItemApiAdapter {
    // 금융상품 정보 반환
    List<BankItemResponse> getBankItemList(String financeCd);
}
