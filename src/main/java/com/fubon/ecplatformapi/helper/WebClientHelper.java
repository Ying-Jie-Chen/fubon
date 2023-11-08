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

    public <T1, T2> Mono<T2> callWebClient(String uri, T1 requestBody, Class<T2> responseClass) {
        return WebClient.create()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseClass)
                .log();
    }

    public <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> getMonoZip(Mono<T1> mono1, Mono<T2> mono2, Mono<T3> mono3, Mono<T4> mono4) {
        return Mono.zip(mono1, mono2, mono3, mono4);
    }

}

