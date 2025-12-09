package org.nextme.account_server.account.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.nextme.account_server.global.common.jpa.BaseEntity;


@Table(name = "p_bank_code",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_code", "fin_co_no"}))
@Getter
@ToString
@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank extends BaseEntity {

    @EmbeddedId
    @Column(name = "bank_id")
    // 은행ID
    private BankId bankId;

    @Column(name = "bank_code", nullable = false)
    // codef 은행코드
    private String bankCode;

    @Column(name = "fin_co_no", nullable = false)
    // 금감원 은행코드
    private String finCoNo;

    @Column(name = "bank_name", nullable = false)
    // 은행이름
    private String bankName;

}
