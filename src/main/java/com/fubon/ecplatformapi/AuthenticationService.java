package com.fubon.ecplatformapi;

//@Service
//public class AuthenticationService {
//
//    private static final String FUBON_API_URL = "https://jsonplaceholder.typicode.com/todos/1";
//
//    private final WebClient webClient;
//
//    public AuthenticationService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
//    }
//
//    public LoginResponse login(LoginReq request) {
//
//        // 建構請求對象
//        FbecRequest fbecRequest = FbecRequest.builder()
//                .returnPfx("0")
//                .identify(request.getIdentify())
//                .empNo(request.getAccount())
//                .verificationCode(request.getVerificationCode())
//                .token(request.getToken())
//                .build();
//        // 發送請求到 Fubon API
//        FbecResponse fbecResponse = webClient.post()
//                .uri("/FBECAPPCERT1001")
//                .header("Content-Type", "application/json")
//                .body(BodyInserters.fromValue(fbecRequest))
//                .retrieve()
//                .bodyToMono(FbecResponse.class)
//                .block();
//
//        // 檢查分分驗證結果
//        if (fbecResponse != null && fbecResponse.isStaffValid()) {
//
//            return LoginResponse.builder()
//                    .code("0000")
//                    .message("登錄成功")
//                    .data(null)
//                    .build();
//
//        } else {
//            return LoginResponse.builder()
//                    .code(StatusCodeEnum.Err10001.name())
//                    .message(StatusCodeEnum.Err10001.getMessage())
//                    .data(null)
//                    .build();
//        }
//
//    }
//
//
//}
//
