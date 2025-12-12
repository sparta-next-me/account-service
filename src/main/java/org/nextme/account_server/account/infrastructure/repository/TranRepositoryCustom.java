package org.nextme.account_server.account.infrastructure.repository;

import org.nextme.account_server.account.domain.entity.Tran.Tran;

import java.util.UUID;

public interface TranRepositoryCustom {

    Tran tranRequest(String tranDate, String tranTime, int deposit,int withDraw); // 생성 시 조회용

    Tran tranSelectRequest(UUID userId,String tranDate, String tranTime, int deposit, int withDraw); // 조회용
}
