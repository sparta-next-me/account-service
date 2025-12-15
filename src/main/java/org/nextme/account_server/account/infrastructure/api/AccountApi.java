package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.domain.AccountApiAdapter;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountRequest;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountApi implements AccountApiAdapter {

    private final ObjectMapper objectMapper;

    //@Value("${ACCESS_TOKEN}")
    private String accessToken ="Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXJ2aWNlX3R5cGUiOiIxIiwic2NvcGUiOlsicmVhZCJdLCJzZXJ2aWNlX25vIjoiMDAwMDA2NDMzMDAyIiwiZXhwIjoxNzY2MDI0NDc1LCJhdXRob3JpdGllcyI6WyJJTlNVUkFOQ0UiLCJQVUJMSUMiLCJCQU5LIiwiRVRDIiwiU1RPQ0siLCJDQVJEIl0sImp0aSI6IjllNmNhNDgyLTRiNTYtNDJlOS05ZTkyLTY0ZGQ2OWU4YzY3MiIsImNsaWVudF9pZCI6IjQyNzg2Yjg1LTgwMzktNDU3ZC1hNzkzLTRhMjkwN2IzOGU1MCJ9.gNKJnz0s2GT2f5kwmDujmHHZdDj1YiqZkHHvI9_b4q94nxCSzkRDOjpjRHHRjjJYdIA_WjezRZ2AeEuzJy_QKqEC8yE7MjtpN-S9gDUWJo07BjARmN29j0-v3FfHQ4PkKppfKez0K3s5zM3gNO4p--Cz8Eu40Ceke9NlS2PepLF58-hSlGlkrGtITdTHskaNERi5lSwt-0xbZjk0mcV6eEim5qlaCBpjeWeF8qzLTsJBQWIM7YCZ2yTblzEfJHpv0wBdJNG2P-f5qQwuYtV9hHyC1tUwdKMEFIGy42T9gvEVkM31WAwN7BQdHsodArKbWe8qCFDylPve3LL08VUMRA";


    @Override
    public String getAccount(AccountRequest request) {

        String url = "https://development.codef.io/v1/kr/bank/p/account/account-list";

        try{
            // 계졍 연동 api 호출
            ResponseEntity<String> response = RestClient.create()
                    .post()
                    .uri(url)
                    .header("Authorization",   accessToken)
                    .body(request)
                    .retrieve()
                    .toEntity(String.class);

            // 호출하여 상태값 확인
            HttpStatusCode status = response.getStatusCode();

            // 상태값이 2xx일 때만
            if (status.is2xxSuccessful()) {
                // 응답값이 인코딩 돼서 오기 때문에 디코딩으로 해줘야 함
                String conn = URLDecoder.decode(response.getBody(), StandardCharsets.UTF_8);

                //Json 파싱하기 위해 디코딩 된 응답값 가져옴
                JsonNode root = objectMapper.readTree(conn);

                // 정보가 있는 객체 접근
                JsonNode data = root.path("data").path("resDepositTrust");

                // 파싱 후 resDepositTrust의 값이 비어있으면 응답값이 제대로 오지 않음
                if(!data.isArray()|| data.isEmpty()){
                    throw new ApiException(ApiErrorCode.API_RESPONSE_EMPTY);
                }else{
                    // 계좌번호가 있는 배열 요소 접근
                    // 여러 계좌를 가져오기 때문에 지금은 제일 앞에 있는 계좌 연동으로 구현
                    JsonNode resAccount = root.path("data").path("resDepositTrust").get(0).path("resAccount");
                    String accountNumber = resAccount.asText();
                    // 계좌 연동 시 특정 계좌 선택하려면
//                    List<String> accountList = new ArrayList<>();
//
//                    if (trustList.isArray()) {
//                        for (JsonNode item : trustList) {
//                            String account = item.path("resAccountDisplay").asText();
//                            accountList.add(account);
//                        }
//                    }

                    return accountNumber;

                }
            }
            throw new ApiException(ApiErrorCode.API_RESPONSE_EMPTY);

        }
        catch (ApiException e){
            throw e;
        }
        catch (Exception e){
            log.error("계좌 조회 API 호출 실패", e);
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
