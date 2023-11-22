package com.fubon.ecplatformapi.controller.auth;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.service.SsoService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fubon.ecplatformapi.config.SessionManager;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController extends SessionController{
    @Autowired
    AuthService authService;
    @Autowired
    SsoService ssoService;

    /**
     * 展業平台登入
     *
     */
    @PostMapping("/login")
    public ResponseEntity<ApiRespDTO<LoginRespVo.ResponseData>> login(@RequestBody LoginReqDTO loginReq, HttpSession session){

        try {

            LoginRespVo responseData = authService.getUserInfo(loginReq, session);
            //SessionHelper.getAllValue();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + responseData.getToken());

            return ResponseEntity.ok().headers(headers)
                    .body(ApiRespDTO.<LoginRespVo.ResponseData>builder()
                            .code(StatusCodeEnum.SUCCESS.getCode())
                            .message(StatusCodeEnum.SUCCESS.getMessage())
                            .authToken(responseData.getToken())
                            .data(responseData.getData())
                            .build());

        } catch (CustomException exception){
            return ResponseEntity.ok().body(systemErrorResp(exception.getMessage()));

        } catch (Exception e) { return ResponseEntity.ok().body(systemErrorResp()); }
    }

    /**
     * 取得圖形驗證碼
     *
     */
    @GetMapping("/getVerificationImage")
    public ApiRespDTO<VerificationVo> getVerificationImage() {
        try {
            VerificationVo responseData = authService.getVerificationImage();
            return successApiResp(responseData);
        } catch (Exception e) {
            return systemErrorResp();
        }
    }

    /**
     * 展業平台登出
     *
     */
    @PostMapping("/logout")
    public ApiRespDTO<String> logout(){
        try {
            SessionManager.removeSession(sessionID());
            return ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            return systemErrorResp();
        }
    }


    /**
     * 取得 SSO Token
     *
     */
    @GetMapping("/getSSOToken")
    public ApiRespDTO<String> getSSOToken() {
        String ssoToken = ssoService.getSSOToken();
        return successApiResp(ssoToken);
    }

    /**
     * 登入SSO驗證
     *
     */
    @PostMapping("/loginSSO")
    public ApiRespDTO<LoginRespVo.ResponseData> SSOLogin(@RequestBody SsoReqDTO ssoReq) {
        try {
            ssoService.verifySSOLogin(ssoReq);
            return successApiResp(null);

        } catch (Exception e) {
            return systemErrorResp();
        }
    }
}

