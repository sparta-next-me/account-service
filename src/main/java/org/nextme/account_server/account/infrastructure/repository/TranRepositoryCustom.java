package org.nextme.account_server.account.infrastructure.repository;

import org.nextme.account_server.account.domain.entity.Tran.Tran;

public interface TranRepositoryCustom {

    Tran tranRequest(String tranDate, String tranTime, int deposit,int withDraw);
}
