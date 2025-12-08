package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.application.tran.exception.TranErrorCode;
import org.nextme.account_server.account.application.tran.exception.TranException;
import org.nextme.account_server.account.domain.AccountDeleteApiAdapter;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountDeleteRequest;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
@RequiredArgsConstructor
public class TranDeleteApi implements AccountDeleteApiAdapter {

    private final ObjectMapper objectMapper;
    private final String STATUS_CODE = "CF-00000";

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Override
    public void deleteAccount(AccountDeleteRequest request) {
        String url ="https://development.codef.io/v1/account/delete";
        try {
            ResponseEntity<String> response = RestClient.create()
                    .post()
                    .uri(url)
                    .header("Authorization", accessToken)
                    .body(request)
                    .retrieve()
                    .toEntity(String.class);

            // 반환 상태가 2xx라면
            if (response.getStatusCode().is2xxSuccessful()) {
                String result = URLDecoder.decode(response.getBody(), StandardCharsets.UTF_8);
                JsonNode tranNode = objectMapper.readTree(result);


                // 결과코드 뽑아오기
                String code = tranNode.get("result").get("code").asText();

                // 정상이 아니라면
                if(!code.equals(STATUS_CODE)) {
                    throw new TranException(TranErrorCode.TRAN_ERROR_CODE);
                }

            }

        } catch (TranException e) {
            throw e;
        } catch (Exception e) {
            log.error("계좌 삭제 API 호출 실패", e);
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
