package org.nextme.account_server.account.infrastructure.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.domain.entity.Tran.QTran;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TranRepositoryImpl implements TranRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Tran tranRequest(String tranDate, String tranTime, int deposit, int withDraw) {
        QTran tran = QTran.tran;

        return queryFactory.selectFrom(tran)
                .where((tran.tranDate.eq(tranDate)
                        .and(tran.tranTime.eq(tranTime)))
                        .and(tran.deposit.eq(deposit)
                                .and(tran.withDraw.eq(withDraw))))
                .fetchOne();


    }
}
