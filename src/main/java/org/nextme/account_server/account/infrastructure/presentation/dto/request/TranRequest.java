package org.nextme.account_server.account.infrastructure.presentation.dto.request;

import java.util.UUID;

public record TranRequest(
 UUID accountId, // 계좌아이디
 String organization, // 은행코드
 String connectedId, // 커넥티드아이디
 String account, // 계좌번호
 String startDate, // 조회 시작일자
 String endDate, // 조회 종료일자
 String orderBy // 조회 정렬(최신순 : 1, 과거: 0)


) {
}
