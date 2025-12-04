package org.nextme.account_server.account.application.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.account.exception.BankErrorCode;
import org.nextme.account_server.account.application.account.exception.BankException;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.Bank;
import org.nextme.account_server.account.domain.entity.BankId;
import org.nextme.account_server.account.domain.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.prefs.BackingStoreException;

@Service
@Transactional
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;


    // 은행코드 생성
    public void create(String bankCode,String finCoNo, String bankName) {

        //은행코드가 이미 있는지 확인
        Bank existing = bankRepository.findByBankCodeAndFinCoNo(bankCode,finCoNo);

        // 이미 존재한다면
        if (existing != null) {
            throw new BankException(BankErrorCode.DUPLICATE_BANK);
        }


        Bank bank = Bank.builder()
                .bankId(BankId.of(UUID.randomUUID()))
                .bankCode(bankCode)
                .finCoNo(finCoNo)
                .bankName(bankName)
                .build();
        bankRepository.save(bank);
    }
}
