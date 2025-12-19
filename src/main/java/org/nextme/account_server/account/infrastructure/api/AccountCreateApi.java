package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.CodefAccountRequest;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.domain.AccountCreateApiAdapter;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.AccountCreateRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class AccountCreateApi implements AccountCreateApiAdapter {
    // 비밀번호 암호화
    @Value("${ACCOUNT_PASSWORD}")
    private String password;

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    private final ObjectMapper objectMapper;

    @Value("${ACCOUNT_PASSWORD}")
    String accountPassword;

    @Override
    public String getConnectedId(AccountCreateRequest request) {

        CodefAccountRequest requestDto =new CodefAccountRequest(
                request.id(),
                request.organization(),
                accountPassword
        );

        Map<String, Object> body = Map.of("accountList", List.of(requestDto));


        String url = "https://development.codef.io/v1/account/create";

        try {
            ResponseEntity<String> response = RestClient.create()
                    .post()
                    .uri(url)
                    .header("Authorization",   "Bearer " +accessToken.trim())
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ApiException(ApiErrorCode.API_RESPONSE_EMPTY);
            }

            // 응답값이 인코딩 돼서 오기 때문에 디코딩으로 해줘야 함
            String conn = URLDecoder.decode(response.getBody(), StandardCharsets.UTF_8);

            //Json 파싱하기 위해 디코딩 된 응답값 가져옴
            JsonNode root = objectMapper.readTree(conn);

            // 정보가 있는 객체 접근
            JsonNode connectedIdNode = root.path("data").path("connectedId");



            // 파싱 후 값이 비어있으면 응답값이 제대로 오지 않음
            if(connectedIdNode == null){
                throw new ApiException(ApiErrorCode.API_RESPONSE_EMPTY);
            }
            String connectedId = connectedIdNode.asText();


            return connectedId;

        }
        catch (ApiException e){
            throw e;
        }
        catch (Exception e){
            log.error("connected API 호출 실패", e);
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
