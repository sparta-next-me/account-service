package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.service.AccountService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //계좌 연동(생성)
    @PostMapping
    public ResponseEntity<CustomResponse<AccountResponse>> createAccount(@RequestBody AccountRequest account) {

        AccountResponse accountResponse = accountService.create(account);
        return ResponseEntity.ok(CustomResponse.onSuccess("계정 연동 되었습니다.",accountResponse));
    }

    // 본인 계좌 전체 조회
    @GetMapping()
    public ResponseEntity<CustomResponse<List<AccountResponse>>> getAccount(@RequestBody String clientId) {
        List<AccountResponse> accountResponse = accountService.getAll(clientId);
        return ResponseEntity.ok(CustomResponse.onSuccess("계정 조회 되었습니다.",accountResponse));
    }

    // 계좌 단건 조회
//    @GetMapping("/{id}")
}
