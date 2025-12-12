package org.nextme.account_server.account.infrastructure.presentation.dto.response;

import lombok.Builder;
import org.nextme.account_server.account.domain.entity.Tran.Tran;

import java.time.LocalDateTime;

@Builder
public record TranResponse(
        String resAccountTrDate, // 거래일자
        String resAccountTrTime, // 거래시각
        int resAccountOut, // 출금 금액
        int resAccountIn, // 입금 금액
        int resAfterTranBalance // 현재 잔액
) {
    public static TranResponse of(Tran tran) {
        return TranResponse.builder()
                .resAccountTrDate(tran.getTranDate())
                .resAccountTrTime(tran.getTranTime())
                .resAccountOut(tran.getWithDraw())
                .resAccountIn(tran.getDeposit())
                .resAfterTranBalance(tran.getCurrentBalance())
//                .tran_type(String.valueOf(tran.getTranType()))
                .build();
    }
}
