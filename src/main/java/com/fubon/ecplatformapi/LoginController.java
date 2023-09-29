package com.fubon.ecplatformapi;

//@Slf4j
//@RestController
//public class LoginController {
//
//    @Autowired
//    LoginService loginService;
//
//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LoginRequest request) {
//
//        return loginService.login(request);
//    }
//}
//@RestController
//public class LoginController {
//
//    @Autowired
//    private FubonApiService fbecService;
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @PostMapping("/login")
//    public ApiRespDTO<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//
//        // 身分驗證服務驗證身分
//        LoginResponse loginResponse = authenticationService.login(loginRequest);
//
//        ApiRespDTO<LoginResponse> response = ApiRespDTO.<LoginResponse>builder()
//                .data(loginResponse)
//                .build();
//
//        return response;
//    }
//
//    @GetMapping(value = "nonblock")
//    public ApiRespDTO<UserInfo> getDemoDataNonBlock() {
//        return ApiRespDTO.<UserInfo>builder()
//                .data(fbecService.testWebClient())
//                .build();
//    }
//
//}
