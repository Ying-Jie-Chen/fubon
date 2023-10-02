package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.req.FbecRequest;
import com.fubon.ecplatformapi.model.dto.resp.FbecResponse;
import com.fubon.ecplatformapi.model.dto.req.LoginRequest;
import com.fubon.ecplatformapi.model.dto.resp.LoginResponse;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class FubonApiService {

    private final WebClient webClient;
    private static final String FAKE_URL = "https://jsonplaceholder.typicode.com/todos/1";


    public FubonApiService() {
        this.webClient = WebClient.create();
    }
    public UserInfo testWebClient(){
        Mono<UserInfo> mono = WebClient.create()
                .get()
                .uri(FAKE_URL)
                .retrieve()
                .bodyToMono(UserInfo.class);
        return mono.block();
    }

    public Mono<LoginResponse> login(LoginRequest request) {

        FbecRequest fbecRequest = FbecRequest.builder()
                .returnPfx("0")
                .identify(request.getIdentify())
                .empNo(request.getAccount())
                .verificationCode(request.getVerificationCode())
                .token(request.getToken())
                .build();


        return webClient.post()
                .uri(FAKE_URL)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(fbecRequest))
                .retrieve()
                .bodyToMono(FbecResponse.class)
                .map(apiResponse -> {
                    // 從 API 響應中提取訊息並建構登錄響應
                    return LoginResponse.builder()
                            .code(String.valueOf(apiResponse.isStaffValid()))
                            .message(apiResponse.getStaffValidMsg())
                            .data(apiResponse.getUserInfo())
                            .build();
                });

    }
}
