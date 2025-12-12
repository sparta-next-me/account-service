package org.nextme.account_server.account.infrastructure.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.bankItem.service.BankItemService;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BanKItemReportResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/financial-products")
@RequiredArgsConstructor
public class BankItemController {

    private final BankItemService bankItemService;
    @PostMapping("/deposit")
    // 금융상품 생성(정기예금)
    public ResponseEntity<CustomResponse> createDeposit(@RequestParam String financeCd) {
        bankItemService.createDeposit(financeCd);
        return ResponseEntity.ok(CustomResponse.onSuccess("금융상품 생성되었습니다."));
    }

    @PostMapping("/saving")
    // 금융상품 생성(적금)
    public ResponseEntity<CustomResponse> createSaving(@RequestParam String financeCd) {
        bankItemService.createSaving(financeCd);
        return ResponseEntity.ok(CustomResponse.onSuccess("금융상품 생성되었습니다."));
    }

    @GetMapping
    //  금융상품 전체조회
    public ResponseEntity<CustomResponse<List<BankItemResponse>>> getAllBankItems() {
        List<BankItemResponse> bankResponse = bankItemService.getAll();

        return ResponseEntity.ok(CustomResponse.onSuccess("금융상품 전체 조회 되었습니다.",bankResponse));
    }

    @GetMapping("/{bankId}")
    // 해당 은행의 금융상품 전체 조회
    public ResponseEntity<CustomResponse<List<BankItemResponse>>> getBankItems(@PathVariable UUID bankId) {
        List<BankItemResponse> bankResponse = bankItemService.getBankAll(bankId);

        return ResponseEntity.ok(CustomResponse.onSuccess("해당은행의 금융상품 전체 조회 되었습니다.",bankResponse));
    }

    @GetMapping("/{bankCode}/{bankItemId}")
    // 해당 은행의 금융상품 단건 조회
    public ResponseEntity<CustomResponse<BankItemResponse>> getBankItem(@PathVariable String bankCode,@PathVariable UUID bankItemId) {
        BankItemResponse bankResponse = bankItemService.getBankItem(bankCode,bankItemId);

        return ResponseEntity.ok(CustomResponse.onSuccess("해당은행의 금융상품 단건 조회 되었습니다.",bankResponse));
    }

    @GetMapping("/report")
    // 미래설계에서 백터화 시키키 위한 금융상품 정보
    public List<BanKItemReportResponse> getEmbeddingReport() {
        return bankItemService.getEmbeddingReport();
    }





}
