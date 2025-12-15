package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entity.Tran.Tran;
import org.nextme.account_server.account.domain.entity.Tran.TranId;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TranFeignResponse(
        UUID userId, //유저아이디
        UUID tranId, // 거래아이디
        String resAccountTrDate, // 거래일자
        String resAccountTrTime, // 거래시각
        int resAccountOut, // 출금 금액
        int resAccountIn, // 입금 금액
        int resAfterTranBalance, // 현재 잔액
        String counterpartyName // 거래명
) {
    public static TranFeignResponse of(Tran tran) {
        return TranFeignResponse.builder()
                .userId(tran.getUserId())
                .tranId(tran.getTranId().getId())
                .resAccountTrDate(tran.getTranDate())
                .resAccountTrTime(tran.getTranTime())
                .resAccountOut(tran.getWithDraw())
                .resAccountIn(tran.getDeposit())
                .resAfterTranBalance(tran.getCurrentBalance())
                .counterpartyName(tran.getCounterPartyName())
                .build();
    }
}
