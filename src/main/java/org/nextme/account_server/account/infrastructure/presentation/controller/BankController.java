package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.service.BankService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @PostMapping
    // 은행코드 생성
    public ResponseEntity<CustomResponse> createAccount(@RequestParam(required = false) String bankCode,
                                                        @RequestParam(required = false) String finCoNo,
                                                        @RequestParam(required = false) String bankName) {
        bankService.create(bankCode,finCoNo,bankName);
        return ResponseEntity.ok(CustomResponse.onSuccess("은행코드 생성되었습니다."));
    }
}
