package org.nextme.account_server.account.domain.entiry;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.nextme.account_server.global.common.jpa.BaseEntity;

import java.util.UUID;

@Table(name = "p_user_account")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {
    @EmbeddedId
    private AccountId id;

    @JoinColumn(nullable = false, name = "bank_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bank bank;

    @Column(nullable = false, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "client_id", unique = true)
    private String clientId;

    @Column(nullable = false, name = "bank_account")
    private String bankAccount;

    @Column(nullable = false, name = "login_count")
    @ColumnDefault("0")
    private int loginCount;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean locked;

    @Column(nullable = false, name = "user_id")
    private UUID userId;



}
