package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account>findByUserId(UUID userId);

    Account findById(AccountId of);


    Account findByIdAndUserIdOrBankAccount(AccountId of, UUID userId, String s);

    Account findByClientIdOrBankAccount(String s, String accountMasked);

    Account findByIdAndUserIdAndClientId(AccountId of, UUID userId, String s);
}
