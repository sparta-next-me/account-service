package org.nextme.account_server.account.application.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.bank.exception.BankErrorCode;
import org.nextme.account_server.account.application.bank.exception.BankException;
import org.nextme.account_server.account.application.tran.exception.TranException;
import org.nextme.account_server.account.domain.AccountApiAdapter;
import org.nextme.account_server.account.domain.AccountCreateApiAdapter;
import org.nextme.account_server.account.domain.AccountDeleteApiAdapter;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.nextme.account_server.account.domain.entity.Bank;
import org.nextme.account_server.account.domain.repository.AccountRepository;
import org.nextme.account_server.account.domain.repository.BankRepository;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.*;

import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountSelectResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final AccountApiAdapter apiAdapter;
    private final AccountDeleteApiAdapter  apiDeleteAdapter;
    private final AccountCreateApiAdapter accountCreateApiAdapter;


    // 계좌 연동
    public AccountResponse create(AccountRequest account,UUID userId) {


        // 필수 요청값을 입력하지 않았을 때
        if(account.connectedId() == null || account.connectedId().isEmpty()){
            throw new ApiException(ApiErrorCode.API_MISSING_PARAMETER);
        }



        String account_Number = apiAdapter.getAccount(account);


        // 계좌번호 마스킹 처리
        String account_masked = account_Number.substring(0,3)+"****"+ account_Number.substring(7);

        Account existing = accountRepository.findByClientIdAndBankAccount(account.connectedId(), account_masked);

        //커넥티드아이디와 계좌번호가 이미 있는지 확인

        if(existing != null){
            throw new AccountException(AccountErrorCode.DUPLICATE_ACCOUNT);
        }



        // 사용자가 입력한 은행코드 있는지 확인
        Bank bankEntity = bankRepository.findByBankCode(account.organization());




        //이미 커넥티드아이디와 계좌가 존재한다면
        if(existing != null) {
            throw new AccountException(AccountErrorCode.DUPLICATE_ACCOUNT);
        }
        // 은행코드가 없다면
        if(bankEntity == null) {
            throw new BankException(BankErrorCode.BANK_NOT_FOUND);
        }
        existing = Account.builder()
                .id(AccountId.of(UUID.randomUUID()))
                .bank(bankEntity)
                .userName(account.userName())
                .clientId(account.connectedId())
                .bankAccount(account_masked)
                .userId(userId)
                .build();
        accountRepository.save(existing);

        return AccountResponse.of(existing);

    }

    // 계좌 전체 조회
    public List<AccountSelectResponse> getAll(UUID userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(AccountSelectResponse::of).collect(Collectors.toList());
    }

    // 계좌 단건 조회
    public AccountSelectResponse getCondition(AccountSelectRequest accountSelectRequest, UUID userId) {

        // 조건 요청값에 대한 조회
        Account account = accountRepository.findByIdOrBankAccount(
                AccountId.of(accountSelectRequest.accountId()),
                accountSelectRequest.connectdId());


        // 조회된 게 없다면
        if(account == null ) {
            throw new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }else{
            return AccountSelectResponse.of(account);
        }


    }

    //계정삭제
    public void delete(AccountDeleteRequest accountDeleteRequest, UUID userId) {
        Account account = accountRepository.findById(AccountId.of(accountDeleteRequest.accountId()));
        // 삭제할 게좌 아이디나 유저 아이디가 없다면
        if(account == null || account.getUserId() == null) {
            throw new AccountException(AccountErrorCode.ACCOUNT_ID_NOT_FOUND);
        }

        // 요청 값이 일치하지 않을 떄(유저아이디, 계좌아이디, 커넥티드아이디)
        if(!account.getUserId().equals(String.valueOf(userId))&& !account.getId().getId().equals(accountDeleteRequest.accountId()) && !account.getClientId().equals(accountDeleteRequest.connectedId())) {
            throw new AccountException(AccountErrorCode.ACCOUNT_VALUE_ERROR);
        }

        // 분산 트랜잭션 일관성 문제 해결
        // 외부 api호출과 로컬 db가 서로 원자성 보장 x
        // saga패턴 적용하여 구현
        try {
            // 1단계: 외부 API 삭제
            apiDeleteAdapter.deleteAccount(accountDeleteRequest);

            try {
                // 2단계: 로컬 삭제
                accountRepository.delete(account);
                log.info("계좌 삭제 완료 - accountId: {}", accountDeleteRequest.accountId());
            } catch (Exception e) {
                // 보상 트랜잭션: 외부 API에 복구 요청 (복구 API가 있다면)
                log.error("로컬 삭제 실패, 보상 트랜잭션 필요 - accountId: {}",
                        accountDeleteRequest.accountId(), e);
                throw new AccountException(AccountErrorCode.ACCOUNT_DELETE_FAILED);
            }
        } catch (TranException e) {
            log.error("외부 API 삭제 실패 - accountId: {}", accountDeleteRequest.accountId(), e);
            throw e;
        }

    }


    public AccountResponse createConnectedId(AccountCreateRequest account, UUID userId,String userName) {
        String connectedId = accountCreateApiAdapter.getConnectedId(account);
        String name = userName;
        AccountRequest request = new AccountRequest(
                account.organization(),
                connectedId,
                name
        );
        // 계정 연동 메소드 호출
        AccountResponse accountResponse = create(request,userId);

        return accountResponse;

    }
}