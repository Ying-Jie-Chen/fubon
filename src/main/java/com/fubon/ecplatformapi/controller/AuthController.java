package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.SessionManager;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    /**
     * 展業平台登入
     *
     */
    @PostMapping("/login")
    public ResponseEntity<ApiRespDTO<FubonLoginRespDTO.UserInfo>> login(@RequestBody LoginReqDTO loginReq, HttpSession session, HttpServletResponse response){

        try {

            LoginRespVo responseData = authService.getUserInfo(loginReq, session, response);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + responseData.getToken());

            return ResponseEntity.ok().headers(headers)
                    .body(ApiRespDTO.<FubonLoginRespDTO.UserInfo>builder()
                            .code(StatusCodeEnum.SUCCESS.getCode())
                            .message(StatusCodeEnum.SUCCESS.getMessage())
                            .authToken(responseData.getToken())
                            .data(responseData.getUserInfo())
                            .build());

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiRespDTO.<FubonLoginRespDTO.UserInfo>builder()
                            .code(StatusCodeEnum.ERR00999.name())
                            .message(StatusCodeEnum.ERR00999.getMessage())
                            .build());
        }
    }

    /**
     * 取得圖形驗證碼
     *
     */
    @GetMapping("/getVerificationImage")
    public ApiRespDTO<VerificationVo> getVerificationImage() {

        try {
            VerificationVo responseData = authService.getVerificationImage();

            // 保存解碼圖片到指定路徑
            authService.saveVerificationImage("/Users/yingjie/Desktop/image.png", responseData.getVerificationImage());

            return ApiRespDTO.<VerificationVo>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiRespDTO.<VerificationVo>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

    /**
     * 展業平台登出
     *
     */
    @PostMapping("/logout")
    public ApiRespDTO<String> logout(HttpServletRequest request){
        try {
            SessionManager.removeSession(SessionHelper.getSessionID(request));

            return ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            return ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }
}
