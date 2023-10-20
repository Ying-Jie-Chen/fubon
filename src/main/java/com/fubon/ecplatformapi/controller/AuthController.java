package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.service.AuthServiceImpl;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.SessionService;
import com.fubon.ecplatformapi.service.SsoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SsoService ssoService;
    @Autowired
    private HttpSession session;

    @PostMapping("/loginSSO")
    public ApiRespDTO<UserInfo> SSOLogin(@RequestBody SsoReqDTO ssoReq) {
        try {
            sessionService.getSessionInfo(session); // 印出session中的值
            //UserInfo responseData = ssoService.perfornSsoLogin(ssoReq);
            ssoService.perfornSsoLogin(ssoReq);
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

    @PostMapping("/logout")
    public ApiRespDTO<String> logout(){
        try {
            sessionService.getSessionInfo(session); // print session values
            sessionService.removeSession(session);

            return ApiRespDTO.<String>builder()
                    .build();
        } catch (Exception e){
            log.error(e.getMessage());
            return  ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @PostMapping("/login")
    public ApiRespDTO<UserInfo> login(@RequestBody LoginReq loginRequest, HttpServletRequest request){
        try {
            UserInfo responseData = authServiceImpl.getUserInfo(loginRequest, request);

            return ApiRespDTO.<UserInfo>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<UserInfo>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @GetMapping("/getVerificationImage")
    public ApiRespDTO<VerificationImageVO> getVerificationImage() {
        try {

            VerificationImageVO responseData = authServiceImpl.getVerificationImage();

            return ApiRespDTO.<VerificationImageVO>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<VerificationImageVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
