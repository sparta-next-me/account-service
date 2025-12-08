package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
}
