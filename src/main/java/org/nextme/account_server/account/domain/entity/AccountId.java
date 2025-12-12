package org.nextme.account_server.account.domain.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountId {
    private UUID id;

    protected AccountId(UUID id) {
        this.id = id;
    }

    public static AccountId of(UUID id) {
        return new AccountId(id);
    }

    public static AccountId of(String id) {
        return AccountId.of(UUID.randomUUID());
    }
}
