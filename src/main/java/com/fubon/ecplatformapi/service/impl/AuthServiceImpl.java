package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.helper.JsonHelper;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.service.TokenService;
import com.fubon.ecplatformapi.service.XrefInfoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    TokenService tokenService;
    @Autowired
    EcwsConfig ecwsConfig;
    @Autowired
    JsonHelper jsonHelper;
    @Autowired
    XrefInfoService xrefInfoService;
    private final WebClient webClient;
    @Autowired
    public AuthServiceImpl(WebClient.Builder webClientBuilder, EcwsConfig ecwsConfig) {
        this.ecwsConfig = ecwsConfig;
        this.webClient = webClientBuilder.baseUrl(ecwsConfig.getDomain()).build();
    }

    /**
     * 取得 Fubon API /GetVerificationImage 的回應結果
     *
     */
    @Override
    public VerificationVo getVerificationImage() {
        String jsonRequest = jsonHelper.convertVerificationConfigToJson(ecwsConfig.verificationConfig());

        VerificationResp verificationResp = callFubonService(jsonRequest, VerificationResp.class).block();
        String imageBase64 = verificationResp != null ?
                verificationResp.getAny().getVerificationImageBase64() : null;
        String token = verificationResp != null ?
                verificationResp.getAny().getToken() : null;

        return VerificationVo.builder()
                .verificationImage(imageBase64)
                .token(token)
                .build();
    }

    /**
     * 取得 Fubon API /Login 的回應結果
     *
     */
    @Override
    public LoginRespVo getUserInfo(LoginReqDTO loginReq, HttpSession session, HttpServletResponse response) throws Exception {

        String jsonRequest = jsonHelper.convertLoginConfigToJson(ecwsConfig.fubonLoginConfig(), loginReq);
        LoginRespDTO fbLoginRespDTO = callFubonService(jsonRequest, LoginRespDTO.class).block();

        SessionManager.saveSession(session, response, fbLoginRespDTO);

        String authToken = tokenService.generateToken(session.getId(), loginReq.getAccount(), System.currentTimeMillis());
        tokenService.saveAuthToken(session, authToken);

        LoginRespVo.ResponseData data = xrefInfoService.getXrefInfoList(fbLoginRespDTO);

        return LoginRespVo.builder()
                .token(authToken)
                .data(data)
                .build();
    }

    private <T> Mono<T> callFubonService(String jsonRequest, Class<T> responseType) {
        return webClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(responseType);
    }

    @Override
    public void saveVerificationImage(String outputPath, String base64String){
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(imageBytes);
            log.info("驗證碼圖片成功存到: " + outputPath);
        } catch (IOException e) {
            log.error("保存驗證碼圖片錯誤: " + e.getMessage());
        }
    }

}
