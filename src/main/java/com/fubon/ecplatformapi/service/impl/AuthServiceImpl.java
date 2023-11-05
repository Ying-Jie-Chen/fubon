package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.SessionManager;
import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.helper.JsonHelper;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonVerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final WebClient webClient;
    @Autowired
    public AuthServiceImpl(WebClient.Builder webClientBuilder, EcwsConfig ecwsConfig) {
        this.ecwsConfig = ecwsConfig;
        this.webClient = webClientBuilder.baseUrl(ecwsConfig.getDomain()).build();
    }

    /**
     * 取得 Fubon API /GetVerificationImage 的回應結果
     *
     * @return VerificationVO
     */
    @Override
    public VerificationVo getVerificationImage() {
        String jsonRequest = jsonHelper.convertVerificationConfigToJson(ecwsConfig.verificationConfig());

        FubonVerificationResp fubonVerificationResp = callFubonService(jsonRequest, FubonVerificationResp.class);
        String imageBase64 = fubonVerificationResp.getAny().getVerificationImageBase64();
        String token = fubonVerificationResp.getAny().getToken();

        return VerificationVo.builder()
                .verificationImage(imageBase64)
                .token(token)
                .build();

    }

    /**
     * 取得 Fubon API /Login 的回應結果
     *
     * @param loginReq LoginReq
     * @return UserInfo
     */
    @Override
    public LoginRespVo getUserInfo(LoginReqDTO loginReq, HttpSession session, HttpServletResponse response) throws Exception {

        String jsonRequest = jsonHelper.convertLoginConfigToJson(ecwsConfig.fubonLoginConfig(), loginReq);
        FubonLoginRespDTO fbLoginRespDTO = callFubonService(jsonRequest, FubonLoginRespDTO.class);

        SessionManager.saveSession(session, response, fbLoginRespDTO);

        String authToken = tokenService.generateToken(session.getId(), loginReq.getAccount(), System.currentTimeMillis());
        tokenService.saveAuthToken(authToken);

        FubonLoginRespDTO.UserInfo userInfo= fbLoginRespDTO.getAny().getUserInfo();

        return LoginRespVo.builder()
                .token(authToken)
                .userInfo(userInfo)
                .build();
    }


    private <T> T callFubonService(String jsonRequest, Class<T> responseType) {
        return webClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(responseType)
                .block();
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
