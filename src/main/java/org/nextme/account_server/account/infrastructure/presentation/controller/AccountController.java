package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.service.AccountService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountCreateResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<CustomResponse<AccountCreateResponse>> createAccount(@RequestBody AccountRequest account) {

        AccountCreateResponse accountResponse = accountService.create(account);
        return ResponseEntity.ok(CustomResponse.onSuccess("계정 연동 되었습니다.",accountResponse));
    }
}
