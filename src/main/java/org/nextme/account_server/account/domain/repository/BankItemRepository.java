package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.BankItem.BankItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankItemRepository extends JpaRepository<BankItem, UUID> {

    BankItem findByFinPrdtCd(String finPrdtCd);
}
