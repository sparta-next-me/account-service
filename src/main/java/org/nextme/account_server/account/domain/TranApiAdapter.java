package org.nextme.account_server.account.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;

import java.util.List;

public interface TranApiAdapter {

    // 거래내역 정보 반환
    List<TranResponse> getTranList(TranRequest request);

}
