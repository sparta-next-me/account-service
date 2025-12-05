package org.nextme.account_server.account.domain.repository;

import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TranRepository extends JpaRepository<Tran, UUID> {
    Tran findByTranDateAndTranTime(String date, String time);
}
