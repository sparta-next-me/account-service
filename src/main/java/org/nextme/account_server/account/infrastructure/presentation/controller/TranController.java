package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.tran.service.TranService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranSelectAllRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranSelectRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BanKItemReportResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranFeignResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/account/tran")
@RequiredArgsConstructor
public class TranController {
    private final TranService tranService;

    @PostMapping
    // 거래내역 api 호출 후 db 저장
    public ResponseEntity<CustomResponse<TranResponse>> createAccount(@RequestBody TranRequest request) {

        TranResponse tranResponse = tranService.create(request);
        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 생성 되었습니다.",tranResponse));
    }

    @PostMapping("/all")
    // 거래내역 전체조회
    public ResponseEntity<CustomResponse<List<TranResponse>>> getAccount(@RequestBody TranSelectAllRequest tranSelectAllRequest) {
        List<TranResponse> tranResponse = tranService.getAll(tranSelectAllRequest);

        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 전체 조회 되었습니다.",tranResponse));
    }

    @PostMapping("/condition")
    // 거래내역 단건 조회
    public ResponseEntity<CustomResponse<TranResponse>> getCondition(@RequestBody TranSelectRequest tranSelectRequest) {
        TranResponse tranResponse = tranService.getCondition(tranSelectRequest);
        return ResponseEntity.ok(CustomResponse.onSuccess("거래내역 단건 조회 되었습니다.",tranResponse));

    }

    @GetMapping("/tranList/{userId}")
    // 미래설계에서 백터화 시키키 위한 거래내역 정보
    public List<TranFeignResponse> getEmbeddingTran(@PathVariable UUID userId) {
        return tranService.getEmbeddingTran(userId);
    }




}
