package org.nextme.account_server.account.domain.entiry.BankItem;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.nextme.account_server.account.domain.entiry.AccountId;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankItemId {
    private UUID id;

    protected BankItemId(UUID id) {
        this.id = id;
    }

    public static BankItemId of(UUID id) {
        return new BankItemId(id);
    }

    public static BankItemId of(String id) {
        return BankItemId.of(UUID.randomUUID());
    }
}
