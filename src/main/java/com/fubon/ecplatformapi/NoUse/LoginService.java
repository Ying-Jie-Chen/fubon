package com.fubon.ecplatformapi.NoUse;

import com.fubon.ecplatformapi.Builber.BuildRequest;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.FbLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.NoUse.captcha.CaptchaService;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class LoginService {
    @Autowired
    private BuildRequest buildRequest;

    @Autowired
    private CaptchaService captchaService;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public LoginService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    public ApiRespDTO<GetUserInfoVo> authLogin(LoginReq loginReq){

        try {
            String userInputCaptcha = loginReq.getVerificationCode();
            log.info("使用者輸入的驗證碼: " + userInputCaptcha);

            // 驗證圖形驗證碼
            boolean captchaValid = captchaService.verifyCaptcha(userInputCaptcha);

            if (!captchaValid) {
                log.error("圖形驗證碼驗證失敗");
//                FubonLoginResp.builder()
//                        .Header(FubonLoginResp.Header.builder()
//                                .StatusCode(StatusCodeEnum.Err10001.name())
//                                .StatusDesc(StatusCodeEnum.Err10001.getMessage())
//                                .build())
//                        .build();
                return ApiRespDTO.<GetUserInfoVo>builder()
                        .code(StatusCodeEnum.ERR00999.getCode())
                        .message(StatusCodeEnum.ERR00999.getMessage())
                        .build();

            } else {
                log.info("圖形驗證碼驗證成功");

                /* 呼叫 POST http://localhost:8080/Login 取得 Fubon API 的回應結果 */
                FbLoginRespDTO fubonResponse = callFubonAPI(loginReq).block();

                // 使用 Mono 的 cache 操作符來確保只呼叫一次外部 API
                //Mono<Boolean> authenticationResult = authenticateWithFubon(fubonLoginReq);

                /* 根據 Fubon API 回應 responseData */


                /* 判斷 isValid, 回傳狀態碼 */

                return null;
            }
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    public Mono<FbLoginRespDTO> callFubonAPI(LoginReq loginReq) {
        log.info("建立 FubonAPI FBECAPPCERT1001 的請求 #Start");
        FbLoginReq fbLoginReq = buildRequest.buildFubonLoginRequest(loginReq);

        log.info("取得Fubon API的回應結果#Start");

        return webClient
                .post()
                .uri("/Login")
                .body(BodyInserters.fromValue(fbLoginReq))
                .retrieve()
                .bodyToMono(FbLoginRespDTO.class);
    }







}
