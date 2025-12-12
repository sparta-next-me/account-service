package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.service.AccountService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountDeleteRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountSelectAllRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountSelectRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountDeleteResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountSelectResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PostMapping("/user-account")
    public ResponseEntity<CustomResponse<List<AccountSelectResponse>>> getAllAccount(@RequestBody AccountSelectAllRequest accountSelectAllRequest) {
        List<AccountSelectResponse> accountResponse = accountService.getAll(accountSelectAllRequest);
        return ResponseEntity.ok(CustomResponse.onSuccess("계좌 조회 되었습니다.",accountResponse));
    }

    // 계좌 단건(조건: 아이디, 계좌번호) 조회
    @PostMapping("/condition")
    public ResponseEntity<CustomResponse<AccountSelectResponse>> getAccount(@RequestBody AccountSelectRequest accountSelectRequest) {
        AccountSelectResponse accountResponse = accountService.getCondition(accountSelectRequest);
        return ResponseEntity.ok(CustomResponse.onSuccess("특정 계좌 정보 조회 되었습니다.",accountResponse));
    }

    //계좌 삭제
    @DeleteMapping
    public ResponseEntity<CustomResponse> deleteAccount(@RequestBody AccountDeleteRequest accountDeleteRequest) {
        accountService.delete(accountDeleteRequest);
        return ResponseEntity.ok(CustomResponse.onSuccess("계좌 삭제 되었습니다."));
    }
}
