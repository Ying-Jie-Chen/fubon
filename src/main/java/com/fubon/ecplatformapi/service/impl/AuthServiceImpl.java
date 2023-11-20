package com.fubon.ecplatformapi.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.helper.ConvertToJsonHelper;
import com.fubon.ecplatformapi.model.dto.FubonEcWsLoginReqDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    TokenService tokenService;
    @Autowired
    EcwsConfig ecwsConfig;
    @Autowired
    ConvertToJsonHelper jsonHelper;
    @Autowired
    XrefInfoService xrefInfoService;
    @Autowired
    ObjectMapper objectMapper;

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
    public LoginRespVo getUserInfo(LoginReqDTO loginReq, HttpSession session, HttpServletResponse response) {
        try {
            FubonEcWsLoginReqDTO fubonEcWsLoginReqDTO = jsonHelper.convertFubonEcWsLoginReq(loginReq);
            //String jsonRequest = jsonHelper.convertLoginConfigToJson(ecwsConfig.fubonLoginConfig(), loginReq);
            //LoginRespDTO fbLoginRespDTO = callFubonService(jsonRequest, LoginRespDTO.class).block();

            LoginRespDTO fbLoginRespDTO = callFubonService(objectMapper.writeValueAsString(fubonEcWsLoginReqDTO), LoginRespDTO.class).block();

            SessionManager.saveSession(session, response, fbLoginRespDTO);

            SessionManager.saveAuthToken(response, session);

            String authToken = tokenService.generateToken(session.getId(), loginReq.getAccount(), System.currentTimeMillis());
            tokenService.saveAuthToken(response, session, authToken);

            LoginRespVo.ResponseData data = xrefInfoService.getXrefInfoList(fbLoginRespDTO);

            return LoginRespVo.builder()
                    .token(authToken)
                    .data(data)
                    .build();

        } catch (CustomException exception) {
            log.error(exception.getMessage());
        } catch (NullPointerException ex){
            log.debug(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    private <T> Mono<T> callFubonService(String jsonRequest, Class<T> responseType) {
        return webClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(responseType);
//                .flatMap(response -> {
//                    if (response instanceof LoginRespDTO) {
//                        String statusCode = ((LoginRespDTO) response).getHeader().getStatusCode();
//                        log.info("Status Code: " + statusCode);
//                        if (!"0000".equals(statusCode)) {
//                            return Mono.error(new CustomException(((LoginRespDTO) response).getHeader().getStatusDesc(), StatusCodeEnum.ERR00998.getCode()));
//                        }
//                    }
//                    return Mono.just(response);
//                });
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
