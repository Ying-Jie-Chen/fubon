package com.fubon.ecplatformapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    @Autowired
    private VerificationService verificationService;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiRespDTO<FubonLoginResp>> login(@RequestBody LoginReq loginRequest) {

        try {
            // 驗證圖形驗證碼
            boolean captchaValid = verificationService.verifyCaptcha(loginRequest.getVerificationCode());

            if (!captchaValid) {
                return createErrorResponse(HttpStatus.UNAUTHORIZED, StatusCodeEnum.Err10001);
            }

            // 呼叫富邦API的 FBECAPPCERT1001 進行身份驗證
            FubonLoginReq fubonLoginRequest = buildFubonLoginRequest(loginRequest);

            // 根據富邦API的回應結果判斷身分驗證是否成功
            Mono<Boolean> authenticationResult = verificationService.authenticateWithFubon(fubonLoginRequest);

            return authenticationResult.flatMap(isValid -> {
                if (isValid) {
                    // 身份验证成功
                    FubonLoginResp fubonLoginResp = buildFubonLoginResponse(); // 构建富邦API的回應
                    return ResponseEntity.ok(createSuccessResponse(fubonLoginResp));

                    return Mono.just(ResponseEntity.ok(response));
                } else {
                    // 身份验证失败
                    ApiRespDTO<FubonLoginResp> response = ApiRespDTO.<FubonLoginResp>builder()
                            .code(StatusCodeEnum.Err10001.name())
                            .message(StatusCodeEnum.Err10001.getMessage())
                            //.data(errorMsg)
                            .build();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }).block(); // 阻塞等待响应
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCodeEnum.Err10001);
        }
    }


    private FubonLoginReq buildFubonLoginRequest(LoginReq loginRequest) {
        FubonLoginReq fubonLoginReq = FubonLoginReq.builder()
                .Header(FubonLoginReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .build())
                .FBECAPPCERT1001RQ(FubonLoginReq.FBECAPPCERT1001.builder()
                        .returnPfx("1")
                        .identify(loginRequest.getIdentify())
                        .empNo(loginRequest.getAccount())
                        .password(loginRequest.getPassword())
                        .verificationCode(loginRequest.getVerificationCode())
                        .token(loginRequest.getToken())
                        .build())
                .build();

        return fubonLoginReq;
    }

    private FubonLoginResp buildFubonLoginResponse() {
        FubonLoginResp fubonLoginResp = FubonLoginResp.builder()
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
                        .userInfo(UserInfo.builder()
                                .agent_id("*****60ZZ")
                                .unionNum("FBLIFE-VP207")
                                .email("*****65@training.fubon.com.tw")
                                .id("******44654")
                                .sales_id("******55006")
                                .signed(false)
                                .tested(false)
                                .build())
                        .build())
                .build();

        return fubonLoginResp;
    }


    private ResponseEntity<ApiRespDTO<FubonLoginResp>> createErrorResponse(HttpStatus status, StatusCodeEnum code, String errorMsg) {
        ApiRespDTO<FubonLoginResp> response = ApiRespDTO.<FubonLoginResp>builder()
                .code(code.name())
                .message(code.getMessage())
                //.data(errorMsg)
                .build();
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ApiRespDTO<FubonLoginResp>> createErrorResponse(HttpStatus status, StatusCodeEnum code) {
        return createErrorResponse(status, code, null);
    }

    private ApiRespDTO<FubonLoginResp> createSuccessResponse(FubonLoginResp fubonLoginResp) {
        ApiRespDTO<FubonLoginResp> response = ApiRespDTO.<FubonLoginResp>builder()
                .data(fubonLoginResp)
                .build();
        return response;
    }



}

