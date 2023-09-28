package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.req.LoginRequest;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.LoginResponse;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
@RestController
public class LoginController {

    @Autowired
    private FubonApiService fbecService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiRespDTO<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        // 身分驗證服務驗證身分
        LoginResponse loginResponse = authenticationService.login(loginRequest);

        ApiRespDTO<LoginResponse> response = ApiRespDTO.<LoginResponse>builder()
                .data(loginResponse)
                .build();

        return response;
    }

    @GetMapping(value = "nonblock")
    public ApiRespDTO<UserInfo> getDemoDataNonBlock() {
        return ApiRespDTO.<UserInfo>builder()
                .data(fbecService.testWebClient())
                .build();
    }

}
