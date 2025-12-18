package org.nextme.account_server.account.application.tran.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.bank.exception.BankErrorCode;
import org.nextme.account_server.account.application.bank.exception.BankException;
import org.nextme.account_server.account.application.bankItem.exception.BankItemErrorCode;
import org.nextme.account_server.account.application.bankItem.exception.BankItemException;
import org.nextme.account_server.account.application.tran.exception.TranErrorCode;
import org.nextme.account_server.account.application.tran.exception.TranException;
import org.nextme.account_server.account.domain.TranApiAdapter;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.nextme.account_server.account.domain.entity.BankItem.BankItem;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.nextme.account_server.account.domain.entity.Tran.TranId;
import org.nextme.account_server.account.domain.repository.AccountRepository;
import org.nextme.account_server.account.domain.repository.TranRepository;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranSelectAllRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranSelectRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BanKItemReportResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranFeignResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.account.infrastructure.repository.TranRepositoryCustom;
import org.nextme.account_server.account.infrastructure.repository.TranRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TranService {
    private final TranApiAdapter tranApiAdapter;
    private final TranRepository tranRepository;
    private final AccountRepository accountRepository;
    private final TranRepositoryCustom tranRepositoryCustom;

    public TranResponse create(TranRequest request, UUID userId) {

        // 필수 입력값을 입력하지 않는다면
        if(request.accountId() == null || request.organization() == null || request.organization().isEmpty()
                || request.connectedId() == null || request.connectedId().isEmpty()
                || request.account() == null || request.account().isEmpty()
                || request.orderBy() == null || request.orderBy().isEmpty()
                || request.startDate() == null || request.startDate().isEmpty()
                || request.endDate() == null || request.endDate().isEmpty()
        ) {
            throw new TranException(TranErrorCode.TRAN_MISSING_PARAMETER);
        }

        log.info("AccountId.of(request.accountId())={}", AccountId.of(request.accountId()));
        log.info("userid={}", userId);
        log.info("request.connectedId()={}", request.connectedId());

        // 계좌 상태 확인
        Account account_status = accountRepository.findByIdAndUserIdAndClientIdAndIsDeletedFalse(AccountId.of(request.accountId()),userId,request.connectedId());

        log.info("account_status={}", account_status.toString());


        // 사용자의 계정이 삭제된 계정이라면
        if(account_status != null) {
            throw new AccountException(AccountErrorCode.ACCOUNT_ID_NOT_FOUND);
        }

        // 사용자 아이디가 존재하지 않는다면
        if(account_status.getUserId() == null) {
            throw new TranException(TranErrorCode.NOT_FOUND_USER_ID);
        }

        log.info("account_status.getUserId={}", account_status.getUserId());

        List<TranResponse> result_tran = tranApiAdapter.getTranList(request);

        log.info("result_tran={}", result_tran.toString());

        // 거래 내역이 없다면
        if(result_tran == null || result_tran.isEmpty()) {
            throw new TranException(TranErrorCode.NOT_FOUND_TRAN);
        }

        Tran tranList = null;
        for(TranResponse tran : result_tran) {

            Tran existing = tranRepositoryCustom.tranRequest(tran.resAccountTrDate(), tran.resAccountTrTime(),tran.resAccountIn(), tran.resAccountOut());
            // 존재한다면
            if(existing != null) {
                throw new TranException(TranErrorCode.DUPLICATE_TRAN);
            }


            tranList = Tran.builder()
                    .tranId(TranId.of(UUID.randomUUID()))
                    .account(account_status)
                    .userId(account_status.getUserId())
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
    public List<TranResponse> getAll(UUID userId) {

        // 계좌 상태 확인
        Account account_status = accountRepository.findByUserIdAndIsDeletedFalse(userId);

        // 사용자의 계정이 삭제된 계정이라면
        if(account_status == null) {
            throw new AccountException(AccountErrorCode.ACCOUNT_ID_NOT_FOUND);
        }
        List<Tran> tranResponse = tranRepository.findByUserId(userId);
        return tranResponse.stream().map(TranResponse::of).collect(Collectors.toList());
    }

    // 거래내역 단건조회
    public TranResponse getCondition(TranSelectRequest tranSelectRequest, UUID userId) {

        // 필수 파라미터를 입력하지 않았다면
        if (userId == null) {
            throw new TranException(TranErrorCode.TRAN_MISSING_PARAMETER);
        }

        if(tranSelectRequest.tranTime() == null && tranSelectRequest.tranDate() == null &&
                tranSelectRequest.deposit() == -1 && tranSelectRequest.withdraw() == -1 ){
            throw new TranException(TranErrorCode.TRAN_MISSING_PARAMETER);
        }

        // 계좌 상태 확인
        Account account_status = accountRepository.findByUserIdAndIsDeletedFalse(userId);

        // 사용자의 계정이 삭제된 계정이라면
        if(account_status == null) {
            throw new AccountException(AccountErrorCode.ACCOUNT_ID_NOT_FOUND);
        }


        // 조건 요청값에 대한 조회
        Tran existing = tranRepositoryCustom.tranSelectRequest(userId,tranSelectRequest.tranDate(), tranSelectRequest.tranTime(), tranSelectRequest.deposit(),tranSelectRequest.withdraw());


        // 조회된 게 없다면
        if(existing == null) {
            throw new TranException(TranErrorCode.NOT_FOUND_TRAN);
        }else{
            return TranResponse.of(existing);
        }
    }

    public List<TranFeignResponse> getEmbeddingTran(UUID userId) {
        List<Tran> tranList = tranRepository.findByUserId(userId);

        // 거래내역이 없다면
        if(tranList.isEmpty()) {
            return Collections.emptyList();
        }

        return tranList.stream().map(TranFeignResponse::of).collect(Collectors.toList());
    }

}