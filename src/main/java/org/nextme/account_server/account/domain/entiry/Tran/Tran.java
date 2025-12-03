package org.nextme.account_server.account.domain.entiry.Tran;

import jakarta.persistence.*;
import lombok.*;
import org.nextme.account_server.account.domain.entiry.Account;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "p_tranList")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tran {

    @EmbeddedId
    private TranId tranId;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "tran_date", nullable = false)
    private LocalDateTime tranDate;

    @Column(name = "withdraw")
    private int withDraw;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "current_balance", nullable = false)
    private int currentBalance;

    @Column(name = "tran_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TranType tranType;

}
