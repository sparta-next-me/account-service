package org.nextme.account_server.account.infrastructure.presentation.dto.response;


import lombok.Builder;
import org.nextme.account_server.account.domain.entity.BankItem.BankItem;

@Builder
public record BankItemResponse(
        String fin_prdt_cd, // 금융상품코드
        String fin_prdt_nm, // 금융상품명
        String join_deny, // 가입제한
        String join_member, // 가입대상
        String spcl_cnd, // 우대조건
        int save_trm, // 저축기간(개월)
        String intr_rate, // 기본금리
        String intr_rate2, // 최대우대금리
        String dcls_strt_day, // 공시시작일
        String dcls_end_day, //공시종료일
        int max_limit, //최고한도
        String item_type, // 금융상품 타입(예금/적금)
        String etc_note // 기타 유의사항


) {
    public static BankItemResponse of(BankItem bankItem) {
        return BankItemResponse.builder()
                .fin_prdt_cd(bankItem.getFinPrdtCd())
                .fin_prdt_nm(bankItem.getFinPrdtNm())
                .join_deny(String.valueOf(bankItem.getJoinDeny()))
                .join_member(String.valueOf(bankItem.getJoinMember()))
                .spcl_cnd(String.valueOf(bankItem.getSpclCnd()))
                .save_trm(Integer.parseInt(bankItem.getSaveTrm()))
                .intr_rate(bankItem.getIntrRate())
                .intr_rate2(bankItem.getIntrRate2())
                .dcls_strt_day(String.valueOf(bankItem.getDclsStrtDay()))
                .dcls_end_day(String.valueOf(bankItem.getDclsEndDay()))
                .max_limit(Integer.parseInt(bankItem.getMaxLimit()))
                .item_type(String.valueOf(bankItem.getItemType()))
                .etc_note(String.valueOf(bankItem.getEtcNote()))
                .build();
    }

}
