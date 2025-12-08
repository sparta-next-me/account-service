package org.nextme.account_server.account.application.tran.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.bank.exception.BankErrorCode;
import org.nextme.account_server.account.application.bank.exception.BankException;
import org.nextme.account_server.account.application.tran.exception.TranErrorCode;
import org.nextme.account_server.account.application.tran.exception.TranException;
import org.nextme.account_server.account.domain.TranApiAdapter;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.nextme.account_server.account.domain.entity.Tran.TranId;
import org.nextme.account_server.account.domain.repository.AccountRepository;
import org.nextme.account_server.account.domain.repository.TranRepository;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.account.infrastructure.repository.TranRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TranService {
    private final TranApiAdapter tranApiAdapter;
    private final TranRepository tranRepository;
    private final AccountRepository accountRepository;
    private final TranRepositoryImpl tranRepositoryImpl;

    public TranResponse create(TranRequest request) {

        // 필수 입력값을 입력하지 않는다면
        if(request.organization() == null || request.organization().isEmpty()
                || request.connectedId() == null || request.connectedId().isEmpty()
                || request.account() == null || request.account().isEmpty()
                || request.orderBy() == null || request.orderBy().isEmpty()
                || request.startDate() == null || request.startDate().isEmpty()
                || request.endDate() == null || request.endDate().isEmpty()
        ) {
            throw new TranException(TranErrorCode.TRAN_MISSING_PARAMETER);
        }

        // 본인 계좌번호의 id값 가져옴
        Account account_id = accountRepository.findByBankAccount(request.account());

        //계좌 아이디가 존재하지 않는다면
        if(account_id == null) {
            throw new AccountException(AccountErrorCode.ACCOUNT_ID_NOT_FOUND);
        }

        // 사용자 아이디가 존재하지 않는다면
        if(account_id.getUserId() == null) {
            throw new TranException(TranErrorCode.NOT_FOUND_USER_ID);
        }

        List<TranResponse> result_tran = tranApiAdapter.getTranList(request);

        // 거래 내역이 없다면
        if(result_tran == null || result_tran.isEmpty()) {
            throw new TranException(TranErrorCode.NOT_FOUND_TRAN);
        }

        Tran tranList = null;
        for(TranResponse tran : result_tran) {

            Tran existing = tranRepositoryImpl.tranRequest(tran.resAccountTrDate(), tran.resAccountTrTime(),tran.resAccountIn(), tran.resAccountOut());
            // 존재한다면
            if(existing != null) {
                throw new TranException(TranErrorCode.DUPLICATE_TRAN);
            }


            tranList = Tran.builder()
                    .tranId(TranId.of(UUID.randomUUID()))
                    .account(account_id)
                    .userId(account_id.getUserId())
                    .tranDate(tran.resAccountTrDate())
                    .tranTime(tran.resAccountTrTime())
                    .withDraw(tran.resAccountOut())
                    .deposit(tran.resAccountIn())
                    .currentBalance(tran.resAfterTranBalance())
                    .build();
            tranRepository.save(tranList);
        }


        return TranResponse.of(tranList);

    }

    // 거래내역 전체조회
    public List<TranResponse> getAll(UUID accountId) {
        List<Tran> tranResponse = tranRepository.findByAccountId(AccountId.of(accountId));
        return tranResponse.stream().map(TranResponse::of).collect(Collectors.toList());
    }

    // 거래내역 단건조회
    public TranResponse getCondition(UUID tranId, String tranDate) {
        // 파라미터 입력하지 않았다면
        if(tranId == null && tranDate==null ) {
            throw new TranException(TranErrorCode.TRAN_MISSING_PARAMETER);
        }
        // 조건 요청값에 대한 조회
        Tran tran = tranRepository.findByTranIdOrTranDate(tranId, tranDate);

        // 조회된 게 없다면
        if(tran == null) {
            throw new TranException(TranErrorCode.NOT_FOUND_TRAN);
        }else{
            return TranResponse.of(tran);
        }
    }
}