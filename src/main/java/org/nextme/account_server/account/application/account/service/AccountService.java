package org.nextme.account_server.account.application.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.domain.ApiAdapter;
import org.nextme.account_server.account.domain.entiry.Account;
import org.nextme.account_server.account.domain.entiry.AccountId;
import org.nextme.account_server.account.domain.entiry.Bank;
import org.nextme.account_server.account.domain.entiry.BankId;
import org.nextme.account_server.account.domain.repository.AccountRepository;
import org.nextme.account_server.account.domain.repository.BankRepository;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;

import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.nextme.account_server.global.infrastructure.exception.ErrorCode.DB_ERROR;
import static org.nextme.infrastructure.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final ApiAdapter apiAdapter;
    
    // 계좌 연동
    public AccountResponse create(AccountRequest account) {
        String account_Number = apiAdapter.getAccount(account);

        Account accountNumber = accountRepository.findByBankAccount(account_Number);

        //커넥티드아이디와 계좌번호가 이미 있는지 확인
        Account existing = accountRepository.findByClientIdAndBankAccount(account.connectedId(), account_Number);

        // 계좌번호 마스킹 처리
        String account_masked = account_Number.substring(0,3)+"****"+ account_Number.substring(7);


//        Bank bankEntity = bankRepository.findById(BankId.of(account.organization()).getId())
//                .orElseThrow(() -> "d");

        // 필수 요청값을 입력하지 않았을 때
        if(account.connectedId() == null || account.connectedId().isEmpty()){
            throw new ApiException(ApiErrorCode.API_MISSING_PARAMETER);
        }

        try{
            //이미 커넥티드아이디와 계좌가 존재한다면
            if(existing != null) {
                throw new AccountException(AccountErrorCode.DUPLICATE_ACCOUNT);
            }
            existing = Account.builder()
                    .id(AccountId.of(UUID.randomUUID()))
//                        .bank()
                    .userName(account.userName())
                    .clientId(account.connectedId())
                    .bankAccount( account_masked)
                    .userId(UUID.randomUUID())

                    .build();
            accountRepository.save(existing);

            return AccountResponse.of(existing);


        }catch (ApiException e){
            throw new ApplicationException(DB_ERROR);
        }

    }

    // 계좌 전체 조회
    public List<AccountResponse> getAll(String clientId) {
        List<Account>  accounts = accountRepository.findAll();
        return accounts.stream().map(AccountResponse::of).collect(Collectors.toList());
    }
}
