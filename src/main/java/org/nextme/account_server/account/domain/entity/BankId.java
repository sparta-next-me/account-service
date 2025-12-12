package org.nextme.account_server.account.domain.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankId {
    private UUID id;

    protected BankId(UUID id) {
        this.id = id;
    }

    public static BankId of(UUID id) {
        return new BankId(id);
    }

    public static BankId of(String id) {
        return BankId.of(UUID.fromString(id));
    }
}
