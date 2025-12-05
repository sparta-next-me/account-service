package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.tran.service.TranService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/tran")
@RequiredArgsConstructor
public class TranController {
    private final TranService tranService;

    @PostMapping
    // 거래내역 api 호출 후 db 저장
    public ResponseEntity<CustomResponse<TranResponse>> createAccount(@RequestBody TranRequest request) {

        TranResponse tranResponse = tranService.create(request);
        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 생성 되었습니다.",tranResponse));
    }


}
