package org.nextme.account_server.account.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountResponse;
import org.nextme.account_server.global.common.jpa.BaseEntity;

import java.util.UUID;

@Table(name = "p_user_account",
        uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "bank_account"}))

@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @EmbeddedId
    // 계좌ID
    private AccountId id;

    @JoinColumn(nullable = false, name = "bank_id")
    @ManyToOne(fetch = FetchType.LAZY)
    // 은행ID
    private Bank bank;

    @Column(nullable = false, name = "user_name")
    // 유저이름
    private String userName;

    // 커넥티드ID
    @Column(nullable = false, name = "client_id")
    //계좌유저ID
    private String clientId;

    @Column(nullable = false, name = "bank_account")
    // 계좌번호
    private String bankAccount;

    @Column(nullable = false, name = "login_count")
    @ColumnDefault("0")
    // 로그인횟수
    private int loginCount;

    @ColumnDefault("false")
    @Column(nullable = false)
    // 계좌 상태
    private boolean locked;

    @Column(nullable = false, name = "user_id")
    // 유저식별ID
    private UUID userId;

}
