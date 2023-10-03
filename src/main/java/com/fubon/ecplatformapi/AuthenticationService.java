package com.fubon.ecplatformapi;

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
    @Autowired
    private ObjectMapper objectMapper;
    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public AuthenticationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }



    public Mono<FubonLoginResp> login(LoginReq loginReq) {
        // Fubon API 的回應
        FubonLoginResp fubonloginResp = buildFubonLoginResponse();

        try {
            // 建立富邦API FBECAPPCERT1001 的請求
            FubonLoginReq fubonLoginReq = buildFubonLoginRequest(loginReq);

            // 使用 Mono 的 cache 操作符來確保只呼叫一次外部 API
            Mono<Boolean> authenticationResult = authenticateWithFubon(fubonLoginReq);

            // 使用 doOnNext 操作符來記錄身份驗證結果
            authenticationResult.doOnNext(isValid -> {
                if (isValid) {
                    log.info("身份驗證成功");
                } else {
                    log.error("身份驗證失敗");
                }
            }).subscribe(); // 訂閱以觸發執行

            return authenticationResult.flatMap(isValid -> {
                if (isValid) {
                    return Mono.just(successResponse(fubonloginResp));
                } else {
                    throw new IllegalArgumentException("身份驗證失敗");
                }
            }).map(ApiRespDTO::getData); // 提取成功回應的數據

        } catch (Exception e) {
            log.error(e.toString());
            return Mono.error(e);
        }
    }

    // 取得 Fubon API 的回應結果
    // POST http://localhost:8080/Login
    // 請求主體 : FubonLoginReq , HTTP 響應的主體: FubonLoginResp
    public Mono<Boolean> authenticateWithFubon(FubonLoginReq request) {
        log.info("取得Fubon API的回應結果#Start");
        return webClient
                .post()
                .uri("/Login")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(FubonLoginResp.class)
                .map(response -> response.getAny().isStaffValid())
                .defaultIfEmpty(false);
    }

    private FubonLoginReq buildFubonLoginRequest(LoginReq loginRequest) {
        log.info("建立富邦API FBECAPPCERT1001 的請求 #Start");

        FubonLoginReq req = FubonLoginReq.builder()
                .Header(FubonLoginReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .build())
                .FBECAPPCERT1001RQ(FubonLoginReq.FBECAPPCERT1001.builder()
                        .returnPfx("0")
                        .identify(loginRequest.getIdentify())
                        .empNo(loginRequest.getAccount())
                        .password(loginRequest.getPassword())
                        .verificationCode(loginRequest.getVerificationCode())
                        .token(loginRequest.getToken())
                        .build())
                .build();

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
        return req;
    }

    private FubonLoginResp buildFubonLoginResponse() {

        FubonLoginResp response = FubonLoginResp.builder()
                .Header(FubonLoginResp.Header.builder()
                        .MsgId("7cdf926e-561a-43a7-bc52-ec947468fc66")
                        .FromSys("ECWS")
                        .ToSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(FubonLoginResp.Any.builder()
                        .staffValid(true)
                        .staffValidMsg("")
                        .build())
                .build();

        return response;
    }

    private ApiRespDTO<FubonLoginResp> successResponse(FubonLoginResp fubonLoginResp) {
        return ApiRespDTO.<FubonLoginResp>builder()
                .data(fubonLoginResp)
                .build();
    }

    private ApiRespDTO<Map<String, Object>> errorResponse() {
        return ApiRespDTO.<Map<String, Object>>builder()
                .code(StatusCodeEnum.Err10001.name())
                .message(StatusCodeEnum.Err10001.getMessage())
                .data(null)
                .build();
    }

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

