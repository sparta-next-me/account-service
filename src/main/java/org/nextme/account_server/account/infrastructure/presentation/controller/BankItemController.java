package org.nextme.account_server.account.infrastructure.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.bankItem.service.BankItemService;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/bank-item")
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
    public ResponseEntity<CustomResponse> createSaving(@RequestParam String financeCd) {
        bankItemService.createSaving(financeCd);
        return ResponseEntity.ok(CustomResponse.onSuccess("금융상품 생성되었습니다."));
    }
}
