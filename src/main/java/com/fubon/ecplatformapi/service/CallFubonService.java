package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.Builber.BuildRequest;
import com.fubon.ecplatformapi.model.dto.req.FbLoginReq;
import com.fubon.ecplatformapi.model.dto.req.FbQueryReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;

@Slf4j
@Service
public class CallFubonService {
    @Autowired
    private BuildRequest buildRequest;
    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public CallFubonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    public Mono<VerificationResp> FBECCOMSTA1032() {
        //log.info("建立 FubonAPI 的請求 #Start");
        //VerificationReq request = buildRequest.buildVerificationImageRequest();

        log.info("Fubon API /GetVerificationImage 的回應結果#Start");
        return webClient
                .get()
                .uri("/GetVerificationImage")
                .retrieve()
                .bodyToMono(VerificationResp.class);
    }

    public Mono<FbLoginRespDTO>     FBECAPPCERT1001(LoginReq loginReq) {
        log.info("建立 FubonAPI 的請求 #Start");
        FbLoginReq request = buildRequest.buildFubonLoginRequest(loginReq);

        log.info("Fubon API /Login 的回應結果#Start");
        return webClient
                .post()
                .uri("/Login")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(FbLoginRespDTO.class);
    }


    public Mono<FbQueryResp> queryResponse(QueryReqDTO queryReq) {
        log.info("建立 FubonAPI 的請求 #Start");
        FbQueryReq request = buildRequest.buildFbQueryRequest(queryReq);

        log.info("Fubon API /QueryList 的回應結果#Start");
        return webClient
                .get()
                .uri("/QueryList")
                //.body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(FbQueryResp.class);

    }
}
