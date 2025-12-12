package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import java.util.UUID;

public record TranSelectAllRequest (
        UUID userId // 유저ID

){
}
