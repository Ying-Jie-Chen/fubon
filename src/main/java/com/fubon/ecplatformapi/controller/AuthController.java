package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.service.SessionService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    SessionService sessionService;

    @PostMapping("/logout")
    public ApiRespDTO<String> logout(HttpSession session){
        try {
            sessionService.removeSession(session);

            return ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .build();
        } catch (Exception e){
            log.error(e.getMessage());
            return  ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

    @PostMapping("/login")
    public  ResponseEntity<ApiRespDTO<GetUserInfoVo>> login(@RequestBody LoginReq loginReq, HttpSession session){

        try {
            LoginRespVo responseData = authService.getUserInfo(loginReq, session);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + responseData.getToken());

            return ResponseEntity.ok().headers(headers)
                    .body(ApiRespDTO.<GetUserInfoVo>builder()
                            .code(StatusCodeEnum.SUCCESS.getCode())
                            .message(StatusCodeEnum.SUCCESS.getMessage())
                            .data(responseData.getGetUserInfoVo())
                            .authToken(responseData.getToken())
                            .build());

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiRespDTO.<GetUserInfoVo>builder()
                            .code(StatusCodeEnum.ERR00999.name())
                            .message(StatusCodeEnum.ERR00999.getMessage())
                            .build());
        }
    }


    @GetMapping("/getVerificationImage")
    public ApiRespDTO<VerificationImageVO> getVerificationImage() {
        try {

            VerificationImageVO responseData = authService.getVerificationImage();


            return ApiRespDTO.<VerificationImageVO>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<VerificationImageVO>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

}
