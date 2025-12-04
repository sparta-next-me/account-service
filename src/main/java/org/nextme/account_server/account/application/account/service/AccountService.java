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
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountCreateResponse;

import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.nextme.account_server.global.infrastructure.exception.ErrorCode.DB_ERROR;
import static org.nextme.infrastructure.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final ApiAdapter apiAdapter;

    @Transactional
    public AccountCreateResponse create(AccountRequest account) {
        String account_Number = apiAdapter.getAccount(account);

        Account accountNumber = accountRepository.findByBankAccount(account_Number);

        //커넥티드아이디와 계좌번호가 이미 있는지 확인
        Account existing = accountRepository.findByClientIdAndBankAccount(account.connectedId(), account_Number);



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
                    .userName("테스트")
                    .clientId(account.connectedId())
                    .bankAccount( account_Number)
                    .userId(UUID.randomUUID())

                    .build();
            accountRepository.save(existing);

            return AccountCreateResponse.of(accountNumber);


        }catch (ApiException e){
            throw new ApplicationException(DB_ERROR);
        }

    }
}
