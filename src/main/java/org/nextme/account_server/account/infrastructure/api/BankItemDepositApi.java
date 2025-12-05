package org.nextme.account_server.account.infrastructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nextme.account_server.account.domain.BankItemDepositApiAdapter;
import org.nextme.account_server.account.infrastructure.exception.ApiErrorCode;
import org.nextme.account_server.account.infrastructure.exception.ApiException;
import org.nextme.account_server.account.infrastructure.presentation.dto.response.BankItemResponse;
import org.nextme.account_server.global.infrastructure.exception.ApplicationException;
import org.nextme.account_server.global.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class BankItemDepositApi implements BankItemDepositApiAdapter {

    private final ObjectMapper objectMapper;

    @Value("${AUTH_KEY}")
    private String authKey;

    @Override
    public List<BankItemResponse> getBankItemDepositList(String financeCd) {

        // 정기예금
        String url = "https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json?auth="+authKey+"&topFinGrpNo=020000&pageNo=1&financeCd="+financeCd;

        try{
            ResponseEntity<String> response = RestClient.create()
                    .get()
                    .uri(url)
                    .retrieve()
                    .toEntity(String.class);

            // 호출하여 상태값 확인
            HttpStatusCode status = response.getStatusCode();

            // 상태값이 2xx일 때만
            if (status.is2xxSuccessful()) {

                // 해당은행의 금융상품 정보 배열
                JsonNode result = objectMapper.readTree(response.getBody()).path("result").path("baseList");

                // Json에 담긴 금융상품 객체화를 위한 List 생성
                List<BankItemResponse> bankItemResponseList = new ArrayList<>();

                if(result.isArray()){
                    // 배열에 있는 거 한 행씩 디비에 담기 위한 객체화
                    for(JsonNode item : result){
                        // 객체로 만들어서 응답값으로 넘김
                        BankItemResponse bankItemResponse = objectMapper.treeToValue(item, BankItemResponse.class);
                        bankItemResponseList.add(bankItemResponse);
                    }
                }

                return bankItemResponseList;

            }else{
                throw new ApiException(ApiErrorCode.API_REQUEST_INVALID);
            }
        }catch (Exception e) {
            log.error("금융상품 조회 API 호출 실패", e);
            throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
