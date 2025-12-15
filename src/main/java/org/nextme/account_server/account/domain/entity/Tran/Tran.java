package org.nextme.account_server.account.domain.entity.Tran;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.global.common.jpa.BaseEntity;


import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "p_tran_List")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tran extends BaseEntity {

    @EmbeddedId
    // 거래ID
    private TranId tranId;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    // 계좌ID
    private Account account;

    @Column(name = "user_id", nullable = false)
    // 유저ID
    private UUID userId;

    @Column(name = "tran_date", nullable = false)
    // 거래일자
    private String tranDate;

    //거래시간
    private String tranTime;

    @Column(name = "withdraw")
    @ColumnDefault("-1")
    // 출금금액
    private int withDraw;

    @Column(name = "deposit")
    @ColumnDefault("-1")
    // 입금금액
    private int deposit;

    @Column(name = "current_balance", nullable = false)
    @ColumnDefault("-1")
    // 현재잔액
    private int currentBalance;

    private String counterPartyName; // 거래 입출금명

}
