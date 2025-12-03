package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entiry.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
