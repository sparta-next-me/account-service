package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.Account;
import org.nextme.account_server.account.domain.entity.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByBankAccount(String accountNumber);

    Account findByClientIdAndBankAccount(String clientId, String accountNumber);


    Account findByIdOrBankAccount(AccountId id, String bankAccount);

    List<Account> findByClientId(String clientId);

    List<Account>findByUserId(UUID userId);

    Account findById(AccountId of);


    Account findByUserIdAndIsDeletedFalse(UUID userId);

    Account findByIdAndIsDeletedFalse(AccountId of);


    Account findByIdAndUserIdAndClientIdAndIsDeletedFalse(AccountId id, UUID userId, String s);

    Account findByAccountIdAndUserIdAndClientIdAndIsDeletedFalse(AccountId id, UUID userId, String account);

}
