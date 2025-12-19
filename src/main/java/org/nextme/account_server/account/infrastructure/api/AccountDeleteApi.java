package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.application.account.exception.AccountErrorCode;
import org.nextme.account_server.account.application.account.exception.AccountException;
import org.nextme.account_server.account.application.tran.exception.TranErrorCode;
import org.nextme.account_server.account.application.tran.exception.TranException;
import org.nextme.account_server.account.domain.AccountDeleteApiAdapter;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountDeleteRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.CodefDeleteAccountRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.AccountDeleteResponse;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class AccountDeleteApi implements AccountDeleteApiAdapter {

    private final ObjectMapper objectMapper;
    private final String STATUS_CODE = "CF-00000";

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Override
    public List<AccountDeleteResponse> deleteAccount(CodefDeleteAccountRequest codefDeleteAccountRequest,AccountDeleteRequest request) {


        /*
        * {

            "accountList":[
                {
                    "countryCode": "KR",
                    "businessType": "BK",
                    "clientType": "P",
                    "organization": "0088",
                    "loginType": "1"
                }
            ],
            "connectedId":"86-x1xqKQzqb-7LwAnGKvU",
            "accountId" :"a8f94952-e335-417d-979f-b398cc85818d"


        * */
        // 위와 같은 형태로 담기 위한 처리
        Map<String, Object> body = Map.of(
                "accountList", List.of(codefDeleteAccountRequest),
                "userId", request.getUserId(),
                "connectedId" , request.getConnectedId(),
                "accountId", request.getAccountId()
        );

        String url ="https://development.codef.io/v1/account/delete";
        try {
                ResponseEntity<String> response = RestClient.create()
                    .post()
                    .uri(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);


                // 반환 상태가 2xx라면
                if (!response.getStatusCode().is2xxSuccessful()) {
                    return new ArrayList<>();
                }
                String result = URLDecoder.decode(response.getBody(), StandardCharsets.UTF_8);
                JsonNode accountNode = objectMapper.readTree(result);


                // 결과코드 추출 (null 체크 포함)
                JsonNode resultNode = accountNode.path("result");
                JsonNode codeNode = resultNode.path("code");


                if (codeNode.isMissingNode()) {
                   log.error("응답에 결과 코드가 없습니다: {}", result);
                    throw new AccountException(AccountErrorCode.ACCOUNT_ERROR_CODE);
                }
                String code = codeNode.asText();

                if (!code.equals(STATUS_CODE)) {
                    log.warn("계좌 삭제 실패 - 코드: {}", code);
                    throw new AccountException(AccountErrorCode.ACCOUNT_DELETE_FAILED);
                }

                List<AccountDeleteResponse> accountList = new ArrayList<>();
                if (accountNode.isArray()) {
                    for (JsonNode node : accountNode) {
                        AccountDeleteResponse tran = objectMapper.treeToValue(node, AccountDeleteResponse.class);
                        accountList.add(tran);
                    }
                }
                log.info("삭제 성공");
                return accountList;

            } catch (TranException e) {
                throw e;
            } catch (Exception e) {
                log.error("계좌 삭제 API 호출 실패", e);
                throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

    }
}
