package org.nextme.account_server.account.infrastructure.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.application.account.service.AccountService;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.*;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountDeleteResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountSelectResponse;
import org.nextme.account_server.global.infrastructure.success.CustomResponse;
import org.nextme.common.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    // 커넥티드 아이디 생성
    @PreAuthorize("hasRole('USER') or hasRole('ADVISOR')")
    @PostMapping("test")
    public ResponseEntity<CustomResponse<AccountResponse>> createAccount(@RequestBody AccountCreateRequest account, @AuthenticationPrincipal UserPrincipal principal) {

        AccountResponse accountResponse = accountService.createConnectedId(account, UUID.fromString(principal.userId()), principal.getName());
        return ResponseEntity.ok(CustomResponse.onSuccess("계정 연동 되었습니다.",accountResponse));
    }


    // 본인 계좌 전체 조회
    @PreAuthorize("hasRole('USER') or hasRole('ADVISOR')")
    @PostMapping("/user-account")
    public ResponseEntity<CustomResponse<List<AccountSelectResponse>>> getAllAccount(@AuthenticationPrincipal UserPrincipal principal) {
        List<AccountSelectResponse> accountResponse = accountService.getAll(UUID.fromString(principal.userId()));
        return ResponseEntity.ok(CustomResponse.onSuccess("계좌 조회 되었습니다.",accountResponse));
    }

    // 계좌 단건(조건: 아이디, 계좌번호) 조회
    @PreAuthorize("hasRole('USER') or hasRole('ADVISOR')")
    @PostMapping("/condition")
    public ResponseEntity<CustomResponse<AccountSelectResponse>> getAccount(@RequestBody AccountSelectRequest accountSelectRequest, @AuthenticationPrincipal UserPrincipal principal) {
        AccountSelectResponse accountResponse = accountService.getCondition(accountSelectRequest, UUID.fromString(principal.userId()));
        return ResponseEntity.ok(CustomResponse.onSuccess("특정 계좌 정보 조회 되었습니다.",accountResponse));
    }

    //계좌 삭제
    @PreAuthorize("hasRole('USER') or hasRole('ADVISOR')")
    @DeleteMapping
    public ResponseEntity<CustomResponse> deleteAccount(@RequestBody AccountDeleteRequest accountDeleteRequest,@AuthenticationPrincipal UserPrincipal principal) {
        accountService.delete(accountDeleteRequest,UUID.fromString(principal.userId()));
        return ResponseEntity.ok(CustomResponse.onSuccess("계좌 삭제 되었습니다."));
    }
}