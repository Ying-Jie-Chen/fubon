package com.fubon.ecplatformapi.NoUse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
public class AuthenticationService {

    public FubonLoginResp login(FubonLoginReq fubonLoginReq) {
        log.info("Into Fubon Service");

        /* 取得 LoginReq 並帶入 FubonReq */


        return null;
//        try {

//            // 使用 doOnNext 操作符來記錄身份驗證結果
//                authenticationResult.doOnNext(isValid -> {
//                    if (isValid) {
//                        log.info("身份驗證成功");
//
//                    } else {
//                        log.error("身份驗證失敗");
//                        FubonLoginResp response = FubonLoginResp.builder()
//                                .Header(FubonLoginResp.Header.builder()
//                                        .StatusCode(StatusCodeEnum.Err10001.name())
//                                        .StatusDesc(StatusCodeEnum.Err10001.getMessage())
//                                        .build())
//                                .Any(FubonLoginResp.Any.builder()
//                                        .staffValid(false)
//                                        .staffValidMsg("身份驗證碼失敗！")
//                                        .build())
//                                .build();
//
//                    }
//                }).subscribe(); // 訂閱以觸發執行
//
//            /* 要寫 isValid 和登入資訊做比對邏輯 */
//
//            authenticationResult.flatMap(isValid -> {
//                if (isValid) {
//                    log.info("身份驗證成功");
//                    FubonLoginResp response = FubonLoginResp.builder()
//                            .Header(FubonLoginResp.Header.builder()
//                                    .StatusCode("0000")
//                                    .StatusDesc("成功")
//                                    .build())
//                            .Any(FubonLoginResp.Any.builder()
//                                    .staffValid(true)
//                                    .staffValidMsg("身份驗證成功！")
//                                    .build())
//                            .build();
//
//
//
//                    return Mono.just(response);
//                } else {
//                    log.error("身份驗證失敗");
//                    throw new IllegalArgumentException();
//                }
//            });
//            return FubonLoginResp.builder()
//                    .Header(FubonLoginResp.Header.builder()
//                            .StatusCode("0000")
//                            .StatusDesc("成功")
//                            .build())
//                    .build();
//            } catch (Exception e){
//                log.error(e.getMessage());
//                throw e;
//            }
    }
//
//    // 取得 Fubon API 的回應結果
//    // POST http://localhost:8080/Login
//    // 請求主體 : FubonLoginReq , HTTP 響應的主體: FubonLoginResp
//    public Mono<Boolean> authenticateWithFubon(FubonLoginReq request) {
//        log.info("取得Fubon API的回應結果#Start");
//        return webClient
//                .post()
//                .uri("/Login")
//                .body(BodyInserters.fromValue(request))
//                .retrieve()
//                .bodyToMono(FubonLoginResp.class)
//                .map(response -> response.getAny().isStaffValid())
//                .defaultIfEmpty(false);
//    }


//    public LoginResponse login(LoginReq request) {
//
//        // 建構請求對象
//        FbecRequest fbecRequest = FbecRequest.builder()
//                .returnPfx("0")
//                .identify(request.getIdentify())
//                .empNo(request.getAccount())
//                .verificationCode(request.getVerificationCode())
//                .token(request.getToken())
//                .build();
//        // 發送請求到 Fubon API
//        FbecResponse fbecResponse = webClient.post()
//                .uri("/FBECAPPCERT1001")
//                .header("Content-Type", "application/json")
//                .body(BodyInserters.fromValue(fbecRequest))
//                .retrieve()
//                .bodyToMono(FbecResponse.class)
//                .block();
//
//        // 檢查分分驗證結果
//        if (fbecResponse != null && fbecResponse.isStaffValid()) {
//
//            return LoginResponse.builder()
//                    .code("0000")
//                    .message("登錄成功")
//                    .data(null)
//                    .build();
//
//        } else {
//            return LoginResponse.builder()
//                    .code(StatusCodeEnum.Err10001.name())
//                    .message(StatusCodeEnum.Err10001.getMessage())
//                    .data(null)
//                    .build();
//        }



}

