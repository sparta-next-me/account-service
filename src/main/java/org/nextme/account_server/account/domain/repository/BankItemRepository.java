package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.BankId;
import org.nextme.account_server.account.domain.entity.BankItem.BankItem;
import org.nextme.account_server.account.domain.entity.BankItem.BankItemId;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankItemRepository extends JpaRepository<BankItem, UUID> {

    BankItem findByFinPrdtCd(String finPrdtCd);


    List<BankItem> findByBankBankId(BankId of);

    BankItem findByBankBankCodeAndBankItemId(String bankCode, BankItemId bankItemId);
}
