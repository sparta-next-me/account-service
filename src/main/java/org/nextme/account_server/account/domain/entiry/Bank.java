package org.nextme.account_server.account.domain.entiry;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.nextme.account_server.global.common.jpa.BaseEntity;

import java.util.UUID;

@Table(name = "p_bank_code")
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank {

    @EmbeddedId
    @Column(name = "bank_id")
    private BankId bankId;

    @Column(name = "bank_code", nullable = false)
    private String bankCode;

    @Column(name = "fin_co_no", nullable = false)
    private String finCoNo;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

}
