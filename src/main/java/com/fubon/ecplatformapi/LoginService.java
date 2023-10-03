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
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class LoginService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VerificationService verificationService;

    private static final String FUBON_API_URL = "http://localhost:8080";
    @Autowired
    public LoginService(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
        ;
    }

    public ApiRespDTO<Map<String, Object>> authLogin(LoginReq loginReq){

        try {
            String userInputCaptcha = loginReq.getVerificationCode();
            log.info("使用者輸入的驗證碼: " + userInputCaptcha);

            // 驗證圖形驗證碼
            boolean captchaValid = verificationService.verifyCaptcha(userInputCaptcha);

            if (!captchaValid) {
                log.error("圖形驗證碼驗證失敗");
//                FubonLoginResp.builder()
//                        .Header(FubonLoginResp.Header.builder()
//                                .StatusCode(StatusCodeEnum.Err10001.name())
//                                .StatusDesc(StatusCodeEnum.Err10001.getMessage())
//                                .build())
//                        .build();
                return ApiRespDTO.<Map<String, Object>>builder()
                        .code(StatusCodeEnum.Err10001.name())
                        .message(StatusCodeEnum.Err10001.getMessage())
                        .build();

            } else {
                log.info("圖形驗證碼驗證成功");

                /* 呼叫 POST http://localhost:8080/Login 取得 Fubon API 的回應結果 */

                /* 判斷 isValid, 回傳狀態碼 */

                UserInfo userInfo = new UserInfo();
                Map<String, Object> responseData = new HashMap<>();

                /*
                 * responseData = UserInfo
                 * UserInfo = FubonApi Response
                 */

                return ApiRespDTO.<Map<String, Object>>builder()
                        .data(responseData)
                        .build();
            }
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

}
