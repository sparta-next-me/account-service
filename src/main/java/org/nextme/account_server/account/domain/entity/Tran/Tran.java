package org.nextme.account_server.account.domain.entity.Tran;

import jakarta.persistence.*;
import lombok.*;
import org.nextme.account_server.account.domain.entity.Account;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "p_tran_List",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tranDate", "tranTime"}))
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tran {

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
    // 출금금액
    private int withDraw;

    @Column(name = "deposit")
    // 입금금액
    private int deposit;

    @Column(name = "current_balance", nullable = false)
    // 현재잔액
    private int currentBalance;

//    @Column(name = "tran_type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    // 거래구분
//    private TranType tranType;

}
