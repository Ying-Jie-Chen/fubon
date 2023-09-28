//package com.fubon.ecplatformapi.FubonApi;
//
//import com.fubon.ecplatformapi.DTO.LoginRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class FubonApiService {
//
//    private final RestTemplate restTemplate;
//
////    @Value("${fubon.api.url}") // 富邦 API 的基本 URL，可以配置在 application.properties 中
//    private String fubonApiUrl;
//
//    @Autowired
//    public FubonApiService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public FbecResponse login() {
//        fubonApiUrl = "https://localhost:8080";
//        // 創建富邦 API 的請求參數對象
//        LoginRequest apiRequest = new LoginRequest();
//
//        // 設置富邦 API 的 Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // 創建富邦 API 的請求實體
//        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(apiRequest, headers);
//
//        // 發送 POST 請求到富邦 API
//        ResponseEntity<FbecResponse> response = restTemplate.exchange(
//                fubonApiUrl + "/login", // 富邦 API 的 URL
//                HttpMethod.POST,
//                httpEntity,
//                FbecResponse.class
//        );
//
//        // 返回富邦 API 的響應
//        return response.getBody();
//    }
//
//}
