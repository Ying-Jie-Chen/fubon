package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.service.VerifyService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private VerifyService verifyService;

    @PostMapping("/login")
    public ApiRespDTO<UserInfo> login(@RequestBody LoginReq loginRequest){

        try {
            //ApiRespDTO<UserInfo> responseDto = loginService.authLogin(loginRequest);
            return ApiRespDTO.<UserInfo>builder()
                    //.data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<UserInfo>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    // 在登入驗證時驗證使用者輸入的驗證碼
//     @PostMapping("/login")
//    public ResponseEntity<ApiRespDTO<UserInfo>> login(@RequestBody LoginReq loginRequest) {
//        ApiRespDTO<UserInfo> responseDto = loginService.authLogin(loginRequest);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }


    @GetMapping("/getVerificationImage")
    public ApiRespDTO<Map<String, Object>> getVerificationImage() {
        /* ApiRespDTO<Map<VO, Object>> */
        String imageBase64 = null;
        String token = null;

        try {
            VerificationResp verificationResp = verifyService.callFubonVerification().block();
            imageBase64 = verificationResp.getAny().getVerificationImageBase64();
            token = verificationResp.getAny().getToken();

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("verificationImage", imageBase64);
            responseData.put("token", token);

            return ApiRespDTO.<Map<String, Object>>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<Map<String, Object>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
