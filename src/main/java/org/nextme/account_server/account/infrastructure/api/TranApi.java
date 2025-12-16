//package org.nextme.account_server.account.infrastructure.api;
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.nextme.account_server.account.domain.TranApiAdapter;
//import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
//import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
//import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
//import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClient;
//
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class TranApi implements TranApiAdapter {
//
//    private final ObjectMapper objectMapper;
//    private final RestClient restClient;
//    @Value("${ACCESS_TOKEN}")
//    private String accessToken;
//
//
//    @Override
//    public List<TranResponse> getTranList(TranRequest request) {
//
//        System.out.println("여기 왔음");
//        String url = "https://development.codef.io/v1/kr/bank/p/account/transaction-list";
//
//        ResponseEntity<String> response = restClient
//                .post()
//                .uri(url)
//                .header("Authorization", "Bearer " + accessToken.trim())
//                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
//                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
//                .body(request)
//                .retrieve()
//                .toEntity(String.class);
//
//        // 호출하여 상태값 확인
//        HttpStatusCode status = response.getStatusCode();
//
//        System.out.println(status + " 상태");
//        log.info(status + " 상태");
//        System.out.println(response.getBody() + " 응답");
//        log.info(response.getBody() + " 응답");
//        System.out.println(accessToken + " 토큰값");
//        log.info(accessToken + " 토큰값");
//
//        try {
//
//
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                return new ArrayList<>();
//            }
//
//            String decoded = URLDecoder.decode(response.getBody(), StandardCharsets.UTF_8);
//            JsonNode tranListNode = objectMapper.readTree(decoded)
//                    .path("data")
//                    .path("resTrHistoryList");
//
//            log.info(tranListNode.toString() + " 디코딩");
//
//            List<TranResponse> tranList = new ArrayList<>();
//            if (tranListNode.isArray()) {
//                for (JsonNode node : tranListNode) {
//                    TranResponse tran = objectMapper.treeToValue(node, TranResponse.class);
//                    tranList.add(tran);
//                }
//            }
//
//            return tranList;
//
//        } catch (Exception e) {
//            log.error("거래내역 호출 실패", e);
//            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.domain.TranApiAdapter;
import org.nextme.account_server.account.infrastructure.presentation.dto.request.TranRequest;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.TranResponse;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TranApi implements TranApiAdapter {

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Override
    public List<TranResponse> getTranList(TranRequest request) {

        log.info("CODEF 거래내역 조회 시작");

        String url = "https://development.codef.io/v1/kr/bank/p/account/transaction-list";

        ResponseEntity<String> response = restClient
                .post()
                .uri(url)
                .header("Authorization", "Bearer " + accessToken.trim())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(String.class);

        HttpStatusCode status = response.getStatusCode();
        String body = response.getBody();

        log.info("CODEF 응답 상태: {}", status);
        log.debug("CODEF 원본 응답: {}", body);

        if (!status.is2xxSuccessful() || body == null) {
            log.error("CODEF API 호출 실패: status={}", status);
        }

        try {
            // ❗ 전체 URLDecoder 금지 (JSON 구조 유지)
            JsonNode root = objectMapper.readTree(body);

            JsonNode tranListNode = root
                    .path("data")
                    .path("resTrHistoryList");

            List<TranResponse> tranList = new ArrayList<>();

            if (!tranListNode.isArray()) {
                log.warn("거래내역 배열이 아님: {}", tranListNode);
                return tranList;
            }

            for (JsonNode node : tranListNode) {

                TranResponse tran = objectMapper.treeToValue(node, TranResponse.class);


                tranList.add(tran);
            }

            log.info("CODEF 거래내역 {}건 조회 성공", tranList.size());
            return tranList;

        } catch (Exception e) {
            log.error("CODEF 거래내역 파싱 실패", e);
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * URL-encoded 문자열 안전 디코딩
     */
    private String decode(String value) {
        if (value == null || !value.contains("%")) {
            return value;
        }
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
