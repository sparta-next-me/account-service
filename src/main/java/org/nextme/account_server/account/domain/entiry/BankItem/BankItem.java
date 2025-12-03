package org.nextme.account_server.account.domain.entiry.BankItem;

import jakarta.persistence.*;
import lombok.*;
import org.nextme.account_server.account.domain.entiry.Bank;

import java.util.UUID;

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
    private BankItemId bankItemId;

    @JoinColumn(name = "bank_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Bank bank;

    @Column(name = "fin_prdt_cd", nullable = false)
    private String finPrdt_cd;

    @Column(name = "fin_prdt_nm", nullable = false)
    private String finPrdtNm;

    @Column(name = "join_deny")
    @Enumerated(EnumType.STRING)
    private JoinEligibility joinDeny;

    @Column(name = "joinMember")
    private String joinMember;

    @Column(name = "spcl_cnd")
    private String spclCnd;

    @Column(name = "save_trm")
    private String saveTrm;

    @Column(name = "intr_rate")
    private String intrRate;

    @Column(name = "intr_rate2")
    private String intrRate2;

    @Column(name = "dcls_strt_day")
    private String dclsStrtDay;

    @Column(name = "dcls_end_day")
    private String dclsEndDay;

    @Column(name = "max_limit")
    private String maxLimit;

    @Column(name = "item_type", nullable = false)
    private String itemType;

    @Column(name = "etc_note")
    private String etcNote;


}
