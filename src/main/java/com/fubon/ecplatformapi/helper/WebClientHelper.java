package com.fubon.ecplatformapi.helper;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

public class WebClientHelper {
    public static <T1, T2> Mono<T2> getMono(String uri, T1 requestBody, Class<T2> responseClass) {
        return WebClient.create()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseClass);
    }





}

