package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.helper.JsonHelper;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonVerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.entity.Token;
import com.fubon.ecplatformapi.repository.TokenRepository;
import com.fubon.ecplatformapi.service.SessionService;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    SessionService sessionService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TokenRepository tokenRepository;
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
    public LoginRespVo getUserInfo(LoginReqDTO loginReq, HttpSession session) throws Exception {

        String jsonRequest = jsonHelper.convertLoginConfigToJson(ecwsConfig.fubonLoginConfig(), loginReq);

        FubonLoginRespDTO fbLoginRespDTO = callFubonService(jsonRequest, FubonLoginRespDTO.class);

        sessionService.saveSessionInfo(fbLoginRespDTO, session);

        String empNo = loginReq.getAccount();
        long timestamp = System.currentTimeMillis();

        String authToken = tokenService.generateToken(session.getId(), empNo, timestamp);
        saveUserToken(authToken);

        Map<String, Object> values = SessionHelper.getAllValue(session);
        log.info(values.toString());

        FubonLoginRespDTO.UserInfo userInfo= fbLoginRespDTO.getAny().getUserInfo();

        return LoginRespVo.builder()
                .token(authToken)
                .userInfo(userInfo)
                .build();
    }

    @Override
    public void saveUserToken(String authToken) {
        Token token = Token.builder()
                .token(authToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
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

}
