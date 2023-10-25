package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.entity.Token;
import com.fubon.ecplatformapi.repository.TokenRepository;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    SessionServiceImpl sessionService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TokenRepository tokenRepository;
    private static final String FUBON_API_URL = "http://localhost:8080";
    private final WebClient webClient;
    @Autowired
    public AuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    @Override
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

    @Override
    public LoginRespVo getUserInfo(LoginReq loginReq, HttpSession session) throws Exception {

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
        sessionService.saveSessionInfo(fbLoginRespDTO, session);

        String empNo = loginReq.getAccount();
        long timestamp = System.currentTimeMillis();

        String authToken = tokenService.generateToken(session.getId(), empNo, timestamp);
        saveUserToken(authToken);

        SessionHelper.getAllValue(session);

        return LoginRespVo.builder()
                .token(authToken)
                .userInfo(fbLoginRespDTO.getAny().getUserInfo())
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

}
