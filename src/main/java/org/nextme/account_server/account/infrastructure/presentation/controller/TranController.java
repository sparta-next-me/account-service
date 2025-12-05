package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.tran.service.TranService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{accountId}")
    // 거래내역 전체조회
    public ResponseEntity<CustomResponse<List<TranResponse>>> getAccount(@PathVariable UUID accountId) {
        List<TranResponse> tranResponse = tranService.getAll(accountId);

        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 전체 조회 되었습니다.",tranResponse));
    }

    @GetMapping("/condition")
    // 거래내역 단건 조회 (아이디, 거래일자)
    public ResponseEntity<CustomResponse<TranResponse>> getCondition(@RequestParam(required = false) UUID tranId, @RequestParam(required = false) String tranDate) {
        TranResponse tranResponse = tranService.getCondition(tranId, tranDate);
        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 단건 조회 되었습니다.",tranResponse));

    }



}
