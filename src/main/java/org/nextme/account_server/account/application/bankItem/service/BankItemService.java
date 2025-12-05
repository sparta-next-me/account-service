package org.nextme.account_server.account.application.bankItem.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.nextme.account_server.account.application.bank.exception.BankErrorCode;
import org.nextme.account_server.account.application.bank.exception.BankException;
import org.nextme.account_server.account.application.bankItem.exception.BankItemErrorCode;
import org.nextme.account_server.account.application.bankItem.exception.BankItemException;
import org.nextme.account_server.account.domain.BankItemDepositApiAdapter;
import org.nextme.account_server.account.domain.BankItemSavingApiAdapter;
import org.nextme.account_server.account.domain.entity.Bank;
import org.nextme.account_server.account.domain.entity.BankItem.BankItem;
import org.nextme.account_server.account.domain.entity.BankItem.BankItemId;
import org.nextme.account_server.account.domain.entity.BankItem.BankItemType;
import org.nextme.account_server.account.domain.entity.BankItem.JoinEligibility;
import org.nextme.account_server.account.domain.repository.BankItemRepository;
import org.nextme.account_server.account.domain.repository.BankRepository;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BankItemService {
    private final BankItemRepository bankItemRepository;
    private final BankItemDepositApiAdapter bankItemDepositApiAdapter;
    private final BankItemSavingApiAdapter bankItemSavingApiAdapter;
    private final BankRepository bankRepository;

    public void createDeposit(String financeCd) {
        // 파라미터 입력하지 않았다면
        requireParameter(financeCd);

        List<BankItemResponse> result =  bankItemDepositApiAdapter.getBankItemDepositList(financeCd);

        // 금융감독원의 api 호출 시 금융상품이 존재하지 않는다면
        bankItemNotFound(result);

        BankItem bankItemList = null;
        for(BankItemResponse bankItem : result) {

            // db에 금융상품이 존재하는지
            existingBanKItem(bankItem);

            // db에 금융상품의 은행코드가 존재하는지
            Bank bankCode = bankRepository.findByFinCoNo(financeCd);

            // 존재하지 않는다면
            if(bankCode == null) {
                throw new BankException(BankErrorCode.BANK_NOT_FOUND);
            }

            bankItemList = BankItem.builder()
                    .bankItemId(BankItemId.of(UUID.randomUUID()))
                    .bank(bankCode)
                    .finPrdtCd(bankItem.fin_prdt_cd())
                    .finPrdtNm(bankItem.fin_prdt_nm())
                    .joinDeny(JoinEligibility.fromString(bankItem.join_deny()))
                    .joinMember(bankItem.join_member())
                    .spclCnd(bankItem.spcl_cnd())
                    .saveTrm(String.valueOf(bankItem.save_trm()))
                    .intrRate(String.valueOf(bankItem.intr_rate()))
                    .intrRate2(String.valueOf(bankItem.intr_rate2()))
                    .dclsStrtDay(String.valueOf(bankItem.dcls_strt_day()))
                    .dclsEndDay(String.valueOf(bankItem.dcls_end_day()))
                    .maxLimit(String.valueOf(bankItem.max_limit()))
                    .itemType(BankItemType.DEPOSIT)
                    .etcNote(String.valueOf(bankItem.etc_note()))
                    .build();

            bankItemRepository.save(bankItemList);
        }



    }

    // 적금 저장
    public void createSaving(String financeCd) {
        // 파라미터 입력하지 않았다면
        requireParameter(financeCd);

        List<BankItemResponse> result =  bankItemSavingApiAdapter.getBankItemSavingList(financeCd);

        // 금융감독원의 api 호출 시 금융상품이 존재하지 않는다면
        bankItemNotFound(result);

        BankItem bankItemList = null;
        for(BankItemResponse bankItem : result) {

            // db에 금융상품이 존재하는지
            existingBanKItem(bankItem);

            // db에 금융상품의 은행코드가 존재하는지
            Bank bankCode = bankRepository.findByFinCoNo(financeCd);

            // 존재하지 않는다면
            if(bankCode == null) {
                throw new BankException(BankErrorCode.BANK_NOT_FOUND);
            }

            bankItemList = BankItem.builder()
                    .bankItemId(BankItemId.of(UUID.randomUUID()))
                    .bank(bankCode)
                    .finPrdtCd(bankItem.fin_prdt_cd())
                    .finPrdtNm(bankItem.fin_prdt_nm())
                    .joinDeny(JoinEligibility.fromString(bankItem.join_deny()))
                    .joinMember(bankItem.join_member())
                    .spclCnd(bankItem.spcl_cnd())
                    .saveTrm(String.valueOf(bankItem.save_trm()))
                    .intrRate(String.valueOf(bankItem.intr_rate()))
                    .intrRate2(String.valueOf(bankItem.intr_rate2()))
                    .dclsStrtDay(String.valueOf(bankItem.dcls_strt_day()))
                    .dclsEndDay(String.valueOf(bankItem.dcls_end_day()))
                    .maxLimit(String.valueOf(bankItem.max_limit()))
                    .itemType(BankItemType.SAVE)
                    .etcNote(String.valueOf(bankItem.etc_note()))
                    .build();

            bankItemRepository.save(bankItemList);
        }



    }

    // 파라미터 입력하지 않았다면
    public void requireParameter(String financeCd) {
        if(financeCd == null) {
            throw new BankItemException(BankItemErrorCode.BANK_ITEM_MISSING_PARAMETER);
        }
    }

    // 금융감독원의 api 호출 시 금융상품이 존재하지 않는다면
    public void bankItemNotFound(List<BankItemResponse> result) {
        if(result == null || result.isEmpty()) {
            throw new BankItemException(BankItemErrorCode.BANK_ITEM_NOT_FOUND);
        }
    }

    // db에 금융상품이 존재하는지
    public void existingBanKItem(BankItemResponse bankItem) {
        System.out.println(bankItem.fin_prdt_cd() + " 금");
        // 금융상품이 이미 존재한다면
        BankItem bankItemProduct =  bankItemRepository.findByFinPrdtCd((bankItem.fin_prdt_cd()));

        // 존재한다면
        if(bankItemProduct != null) {
            throw new BankItemException(BankItemErrorCode.DUPLICATE_BANK_ITEM);
        }


    }

}
