package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.captcha.VerificationService;
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
    private VerificationService verificationService;
    private final WebClient webClient;
    @Autowired
    public LoginService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();;
    }

    public FubonLoginResp login(LoginReq loginReq){
        FubonLoginResp fubonloginResp = buildFubonLoginResponse(); // Fubon API 的回應

        try {
            String userInputCaptcha = loginReq.getVerificationCode();
            log.info("使用者輸入的驗證碼: " + userInputCaptcha);

            // 驗證圖形驗證碼
            boolean captchaValid = verificationService.verifyCaptcha(userInputCaptcha);

            if (!captchaValid) {
                fubonloginResp.setHeader(FubonLoginResp.Header.builder()
                                .StatusCode(StatusCodeEnum.Err10001.name())
                                .StatusDesc(StatusCodeEnum.Err10001.getMessage())
                                .build());
                log.error("圖形驗證碼驗證失敗");
                throw new RuntimeException();
            }
            log.info("圖形驗證碼驗證成功");

            // 呼叫富邦API的 FBECAPPCERT1001 進行身份驗證
            FubonLoginReq fubonLoginRequest = buildFubonLoginRequest(loginReq);

            // 根據富邦API的回應結果判斷身分驗證是否成功
            Mono<Boolean> authenticationResult = authenticateWithFubon(fubonLoginRequest);

            return authenticationResult.flatMap(isValid -> {
                if (isValid) {
                    log.info("身份驗證成功");
                    return Mono.just(successResponse(fubonloginResp));
                } else {
                    log.error("身份驗證失敗");
                    throw new IllegalArgumentException();
                }
            }).block().getData();
        } catch (Exception e) {
            log.error(e.toString());
            throw e;

        }
    }

    // 取得 Fubon API 的回應結果
    // GET http://localhost:8080/auth/login
    // 請求主體 : FubonLoginReq , HTTP 響應的主體: FubonLoginResp
    public Mono<Boolean> authenticateWithFubon(FubonLoginReq fubonLoginReq) {
        return webClient
                .post()
                .uri("/auth/login")
                .body(BodyInserters.fromValue(fubonLoginReq))
                .retrieve()
                .bodyToMono(FubonLoginResp.class)
                .map(response -> response.getAny().isStaffValid())
                .defaultIfEmpty(false);
    }

    private FubonLoginReq buildFubonLoginRequest(LoginReq loginRequest) {
        FubonLoginReq request = FubonLoginReq.builder()
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

        return request;
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

    private ApiRespDTO<Map<String, Object>> errorResponse() {
        return ApiRespDTO.<Map<String, Object>>builder()
                .code(StatusCodeEnum.Err10001.name())
                .message(StatusCodeEnum.Err10001.getMessage())
                .data(null)
                .build();
    }

    private ApiRespDTO<FubonLoginResp> successResponse(FubonLoginResp fubonLoginResp) {
        return ApiRespDTO.<FubonLoginResp>builder()
                .data(fubonLoginResp)
                .build();
    }

}
