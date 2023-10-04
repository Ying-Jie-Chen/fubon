package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.Builber.BuildRequest;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class CallFubonService {
    @Autowired
    private BuildRequest buildRequest;
    private static final String FUBON_API_URL = "http://localhost:8080";

    private WebClient webClient;
    @Autowired
    public CallFubonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    public Mono<VerificationResp> FBECCOMSTA1032() {
        log.info("建立 FubonAPI 的請求 #Start");
        //VerificationReq request = buildRequest.buildVerificationImageRequest();

        log.info("取得Fubon API的回應結果#Start");
        return webClient
                .get()
                .uri("/GetVerificationImage")
                .retrieve()
                .bodyToMono(VerificationResp.class);
    }

    public Mono<FubonLoginResp> FBECAPPCERT1001(LoginReq loginReq) {
        log.info("建立 FubonAPI 的請求 #Start");
        FubonLoginReq request = buildRequest.buildFubonLoginRequest(loginReq);

        log.info("取得Fubon API的回應結果#Start");
        return webClient
                .post()
                .uri("/Login")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(FubonLoginResp.class);
    }


}
