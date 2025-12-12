package org.nextme.account_server.account.domain.entity.Tran;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranId {
    private UUID id;

    protected TranId(UUID id) {
        this.id = id;
    }

    public static TranId of(UUID id) {
        return new TranId(id);
    }

    public static TranId of(String id) {
        return TranId.of(UUID.randomUUID());
    }
}
