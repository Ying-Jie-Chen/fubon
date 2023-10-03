package com.fubon.ecplatformapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.captcha.VerificationService;
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
public class LoginService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VerificationService verificationService;
    private final WebClient webClient;
    @Autowired
    public LoginService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();;
    }

    public FubonLoginResp authLogin(LoginReq loginReq){

        try {
            String userInputCaptcha = loginReq.getVerificationCode();
            log.info("使用者輸入的驗證碼: " + userInputCaptcha);

            // 驗證圖形驗證碼
            boolean captchaValid = verificationService.verifyCaptcha(userInputCaptcha);

            if (!captchaValid) {
                log.error("圖形驗證碼驗證失敗");
                return FubonLoginResp.builder()
                        .Header(FubonLoginResp.Header.builder()
                                .StatusCode(StatusCodeEnum.Err10001.name())
                                .StatusDesc(StatusCodeEnum.Err10001.getMessage())
                                .build())
                        .build();
            }else {
                log.info("圖形驗證碼驗證成功");

                // 建立富邦API FBECAPPCERT1001 的請求
                FubonLoginReq fubonLoginReq = buildFubonLoginRequest(loginReq);

                // 使用 Mono 的 cache 操作符來確保只呼叫一次外部 API
                Mono<Boolean> authenticationResult = authenticateWithFubon(fubonLoginReq).cache();

                // 使用 doOnNext 操作符來記錄身份驗證結果
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

                /* 要寫 isValid 和登入資訊做比對邏輯 */

                authenticationResult.flatMap(isValid -> {
                    if (isValid) {
                        log.info("身份驗證成功");
                        FubonLoginResp response = FubonLoginResp.builder()
                                .Header(FubonLoginResp.Header.builder()
                                        .StatusCode("0000")
                                        .StatusDesc("成功")
                                        .build())
                                .Any(FubonLoginResp.Any.builder()
                                        .staffValid(true)
                                        .staffValidMsg("身份驗證成功！")
                                        .build())
                                .build();

                        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
                        String jsonRequest = null;
                        try {
                            jsonRequest = objectMapper.writeValueAsString(response);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(jsonRequest);

                        return Mono.just(response);
                    } else {
                        log.error("身份驗證失敗");
                        throw new IllegalArgumentException();
                    }
                });
                return FubonLoginResp.builder()
                        .Header(FubonLoginResp.Header.builder()
                                .StatusCode("0000")
                                .StatusDesc("成功")
                                .build())
                        .build();
            }
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    // 取得 Fubon API 的回應結果
    // POST http://localhost:8080/Login
    // 請求主體 : FubonLoginReq , HTTP 響應的主體: FubonLoginResp
    public Mono<Boolean> authenticateWithFubon(FubonLoginReq request) {
        log.info("取得 FubonAPI Response #Start");

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
        log.info("建立 FubonAPI FBECAPPCERT1001 的請求 #Start");

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
}
