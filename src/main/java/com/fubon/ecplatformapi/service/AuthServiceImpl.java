package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.Builber.BuildRequest;
import com.fubon.ecplatformapi.model.dto.req.FbLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.token.Token;
import com.fubon.ecplatformapi.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;


@Slf4j
@Service
public class AuthServiceImpl {

    @Autowired
    private SessionService sessionService;
    @Autowired
    TokenService tokenService;
    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public AuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    public VerificationImageVO getVerificationImage() {

        log.info("Fubon API /GetVerificationImage 的回應結果#Start");

        Mono<VerificationResp> mono = webClient
                .get()
                .uri("/GetVerificationImage")
                .retrieve()
                .bodyToMono(VerificationResp.class);

        VerificationResp verificationResp = mono.block();

        assert verificationResp != null;
        String imageBase64 = verificationResp.getAny().getVerificationImageBase64();
        String token = verificationResp.getAny().getToken();

        VerificationImageVO verificationVO = new VerificationImageVO();
        verificationVO.setVerificationImage(imageBase64);
        verificationVO.setToken(token);

        return verificationVO;
    }

    public UserInfo getUserInfo(LoginReq loginReq, HttpServletRequest request) throws Exception {

        log.info("Fubon API /Login 的回應結果#Start");

        Mono<FbLoginRespDTO> mono = webClient
                .post()
                .uri("/Login")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginReq))
                .retrieve()
                .bodyToMono(FbLoginRespDTO.class);

        FbLoginRespDTO fbLoginRespDTO = mono.block();

        assert fbLoginRespDTO != null;

        HttpSession httpSession = request.getSession();

        String sessionId = httpSession.getId();
        log.info("取得 session ID: " + sessionId);

        sessionService.saveSessionInfo(fbLoginRespDTO, httpSession);

        String empNo = loginReq.getAccount();
        long timestamp = System.currentTimeMillis() / 1000;
        SecretKey AESKey = tokenService.generateAES256Key();
        Token token = tokenService.generateToken(sessionId, empNo, timestamp, AESKey);
        log.info("Token: " + token.getToken());

        sessionService.getSessionInfo(httpSession); // print session values

        UserInfo userInfo = fbLoginRespDTO.getAny().getUserInfo();
        return userInfo;
    }


}
