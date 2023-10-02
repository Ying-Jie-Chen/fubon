package com.fubon.ecplatformapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.FubonLoginReq;
import com.fubon.ecplatformapi.FubonLoginResp;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.VerificationUtil;
import com.fubon.ecplatformapi.model.dto.req.VerificationReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

@Service
public class VerificationService {

    @Autowired
    private ObjectMapper objectMapper;
    private static final int CAPTCHA_EXPIRY_SECONDS = 1200;  // 驗證碼有效期限（20分鐘）
    @Autowired
    private HttpSession session;

    public String generateCaptchaBase64(HttpServletRequest request, HttpServletResponse response) {

        VerificationUtil verificationUtil = new VerificationUtil();
        String captcha = verificationUtil.getRandomCodeBase64(request, response);

        // 将验证码存储在会话中，并设置有效期
        session.setAttribute("captcha", captcha);
        session.setMaxInactiveInterval(CAPTCHA_EXPIRY_SECONDS);

        return captcha;
    }


    // 驗證用戶輸入的驗證碼是否匹配
    public boolean verifyCaptcha(String userInput) {
        // 從會話中取得先前儲存的驗證碼
        String expectedCaptcha = (String) session.getAttribute("captcha");

        // 如果會話中沒有驗證碼或驗證碼過期，則傳回驗證失敗
        if (expectedCaptcha == null) {
            return false;
        }

        // 比較使用者輸入的驗證碼與預期的驗證碼（忽略大小寫）
        return userInput.equalsIgnoreCase(expectedCaptcha);
    }

    public String generateResponseJson(String system, String insureType, String verificationTypes, String base64String) {

        try {

            VerificationReq req = VerificationReq.builder()
                    .Header(VerificationReq.Header.builder()
                            .FromSys("B2A")
                            .SysPwd("*****PW8SGg=")
                            .FunctionCode("FBECCOMSTA1032")
                            .build())
                    .FBECCOMSTA1032RQ(VerificationReq.FBECCOMSTA1032RQ.builder()
                            .system(system)
                            .insureType(insureType)
                            .verificationTypes(verificationTypes)
                            .build())
                    .build();

//            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
//            String jsonRequest = objectMapper.writeValueAsString(req);
//            System.out.println(jsonRequest);

            VerificationRes resp = VerificationRes.builder()
                    .Header(VerificationRes.Header.builder()
                            .MsgId("033ef14f-345e-42a9-9114-fbfdd562909f")
                            .FromSys("ECWS")
                            .ToSys(req.getHeader().getFromSys())
                            .SysPwd(req.getHeader().getSysPwd())
                            .FunctionCode(req.getHeader().getFunctionCode())
                            .StatusCode("0000")
                            .StatusDesc("成功")
                            .build())
                    .any(VerificationRes.Any.builder()
                            .token("2da6cf70570e44658d8fd7e5c334ca03")
                            .verificationImageBase64(base64String)
                            .build())
                    .build();

            return objectMapper.writeValueAsString(resp);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private final WebClient webClient;

    @Autowired
    public VerificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("富邦API的URL").build();
    }

    public Mono<Boolean> authenticateWithFubon(FubonLoginReq loginRequest) {
        return webClient
                .post()
                .uri("/富邦API的路径")
                .body(BodyInserters.fromValue(loginRequest))
                .retrieve()
                .bodyToMono(FubonLoginResp.class)
                .map(response -> response.getAny().isStaffValid())
                .defaultIfEmpty(false);
    }

}
