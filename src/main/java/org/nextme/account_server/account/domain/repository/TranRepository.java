package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.AccountId;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TranRepository extends JpaRepository<Tran, UUID> {
    Tran findByTranDateAndTranTime(String date, String time);


    List<Tran> findByUserId(UUID uuid);

    Tran findFirstByUserId(UUID uuid);

}
