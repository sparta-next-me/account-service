package org.nextme.account_server.account.infrastructure.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.domain.entity.Tran.QTran;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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

    @Override
    public Tran tranSelectRequest(UUID userId, String tranDate, String tranTime, int deposit, int withDraw) {
        QTran tran = QTran.tran;

        BooleanBuilder builder = new BooleanBuilder();

        // userId는 필수
        builder.and(tran.userId.eq(userId));

        // tranDate 조건
        if (tranDate != null) {
            builder.and(tran.tranDate.eq(tranDate));
        }

        // tranTime 조건
        if (tranTime != null) {
            builder.and(tran.tranTime.eq(tranTime));
        }

        // deposit 조건
        if (deposit != -1) {
            builder.and(tran.deposit.eq(deposit));
        }

        // withDraw 조건
        if (withDraw != -1) {
            builder.and(tran.withDraw.eq(withDraw));
        }

        // 단건 조회
        return queryFactory.selectFrom(tran)
                .where(builder)
                .fetchFirst(); // fetchOne() 대신 fetchFirst()가 안전
    }


}
