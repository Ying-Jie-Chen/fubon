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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class VerificationService {
    @Autowired
    private VerificationUtil verificationUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpSession session;

    private static final String SESSION_KEY = "captcha";
//    private static final int CAPTCHA_EXPIRY_SECONDS = 1200;  // 驗證碼有效期限（20分鐘）

    public String generateCaptchaBase64(HttpServletRequest request) {

        String captcha = verificationUtil.getRandomCodeBase64(request);
        log.info("Captcha Base64 : " + captcha);

//        // 將驗證碼存儲在會話中，並設置有效期
//        HttpSession session = request.getSession();
//        session.setAttribute("captcha", captcha);
//        session.setMaxInactiveInterval(CAPTCHA_EXPIRY_SECONDS);

        return captcha;
    }

    // 驗證用戶輸入的驗證碼是否匹配
    public boolean verifyCaptcha(String userInput) {
        // 從會話中取得先前儲存的驗證碼
        String storedCode = (String) session.getAttribute(SESSION_KEY);
        log.info("存在 session 的驗證碼: " + storedCode);

        if (storedCode == null) {
            throw new RuntimeException("session 中沒有驗證碼或驗證碼過期");
        }
        // 比較使用者輸入的驗證碼與預期的驗證碼（忽略大小寫）
        return userInput.equalsIgnoreCase(storedCode);
    }

    public String generateResponseJson(String system, String insureType, String verificationTypes, String token, String base64String) {

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
                            .token(token)
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
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<Boolean> authenticateWithFubon(FubonLoginReq loginRequest) {
        return webClient
                .post()
                .uri("/FBECAPPCERT1001")
                .body(BodyInserters.fromValue(loginRequest))
                .retrieve()
                .bodyToMono(FubonLoginResp.class)
                .map(response -> response.getAny().isStaffValid())
                .defaultIfEmpty(false);
    }

}
