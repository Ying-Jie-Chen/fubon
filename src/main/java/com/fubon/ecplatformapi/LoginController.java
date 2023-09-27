package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.FubonApi.FubonApiResponse;
import com.fubon.ecplatformapi.FubonApi.FubonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private FubonApiService fubonApiService;

    @PostMapping("/login")
    public ResponseEntity<FubonApiResponse> login(@RequestBody LoginRequest request) {
        // 調用富邦 API 登入方法
        FubonApiResponse response = fubonApiService.login();

        // 返回富邦 API 的響應
        return ResponseEntity.ok(response);
    }
}
