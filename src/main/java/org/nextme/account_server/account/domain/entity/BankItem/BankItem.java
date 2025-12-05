package org.nextme.account_server.account.domain.entity.BankItem;

import jakarta.persistence.*;
import lombok.*;
import org.nextme.account_server.account.domain.entity.Bank;

@Table(name = "p_bankItem")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BankItem {

    @EmbeddedId
    @Column(name = "bank_item_id")
    // 금융상품ID
    private BankItemId bankItemId;

    @JoinColumn(name = "bank_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    // 은행ID
    private Bank bank;

    @Column(name = "fin_prdt_cd", nullable = false)
    // 금융상품 코드
    private String finPrdtCd;

    @Column(name = "fin_prdt_nm", nullable = false)
    // 금융상품명
    private String finPrdtNm;

    @Column(name = "join_deny")
    @Enumerated(EnumType.STRING)
    // 가입제한
    private JoinEligibility joinDeny;

    @Column(name = "joinMember")
    // 가입대상
    private String joinMember;

    @Column(name = "spcl_cnd")
    // 우대조건
    private String spclCnd;

    @Column(name = "save_trm")
    // 저축기간(개월)
    private String saveTrm;

    @Column(name = "intr_rate")
    // 기본금리
    private String intrRate;

    @Column(name = "intr_rate2")
    // 최고우대금리
    private String intrRate2;

    @Column(name = "dcls_strt_day", nullable = false)
    // 공시시작일
    private String dclsStrtDay;

    @Column(name = "dcls_end_day")
    // 공시종료일
    private String dclsEndDay;

    @Column(name = "max_limit")
    // 최고한도
    private String maxLimit;

    @Column(name = "item_type", nullable = false)
    @Enumerated(EnumType.STRING)
    // 금융상품타입
    private BankItemType itemType;

    @Column(name = "etc_note")
    // 기타유의사항
    private String etcNote;


}
