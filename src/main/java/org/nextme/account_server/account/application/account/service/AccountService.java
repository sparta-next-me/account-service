package org.nextme.account_server.account.application.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.account.exception.BankErrorCode;
import org.nextme.account_server.account.application.account.exception.BankException;
import org.nextme.account_server.account.domain.ApiAdapter;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.nextme.account_server.account.domain.entity.Bank;
import org.nextme.account_server.account.domain.entity.BankId;
import org.nextme.account_server.account.domain.repository.AccountRepository;
import org.nextme.account_server.account.domain.repository.BankRepository;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;

import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountSelectRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountSelectResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final ApiAdapter apiAdapter;
    
    // 계좌 연동
    public AccountResponse create(AccountRequest account) {
        // 필수 요청값을 입력하지 않았을 때
        if(account.connectedId() == null || account.connectedId().isEmpty()){
            throw new ApiException(ApiErrorCode.API_MISSING_PARAMETER);
        }

        String account_Number = apiAdapter.getAccount(account);

        //커넥티드아이디와 계좌번호가 이미 있는지 확인
        Account existing = accountRepository.findByClientIdAndBankAccount(account.connectedId(), account_Number);

        // 계좌번호 마스킹 처리
        String account_masked = account_Number.substring(0,3)+"****"+ account_Number.substring(7);


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
                .bankAccount( account_masked)
                .userId(UUID.randomUUID())
                .build();
        accountRepository.save(existing);

        return AccountResponse.of(existing);

    }

    // 계좌 전체 조회
    public List<AccountSelectResponse> getAll(String clientId) {
        List<Account>  accounts = accountRepository.findByClientId(clientId);
        return accounts.stream().map(AccountSelectResponse::of).collect(Collectors.toList());
    }

    // 계좌 단건 조회
    public AccountSelectResponse getCondition(AccountSelectRequest accountSelectRequest) {

        // 조건 요청값에 대한 조회
        Account account = accountRepository.findByIdOrBankAccount(
                AccountId.of(accountSelectRequest.accountId()),
                accountSelectRequest.bankAccount());


        // 조회된 게 없다면
        if(account == null ) {
            throw new AccountException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }else{
            return AccountSelectResponse.of(account);
        }


    }
}
