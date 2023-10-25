package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.model.dto.resp.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.service.impl.AuthServiceImpl;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.impl.SessionServiceImpl;
import com.fubon.ecplatformapi.service.impl.SsoServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
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
    AuthServiceImpl authServiceImpl;
    @Autowired
    SessionServiceImpl sessionService;
    @Autowired
    SsoServiceImpl ssoService;

    @PostMapping("/loginSSO")
    public ApiRespDTO<UserInfo> SSOLogin(@RequestBody SsoReqDTO ssoReq, HttpServletRequest request) {
        try {
            //sessionService.getSessionInfo(request.getSession()); // 印出session中的值
            //UserInfo responseData = ssoService.perfornSsoLogin(ssoReq);
            ssoService.perfornSsoLogin(ssoReq);
            return ApiRespDTO.<UserInfo>builder()
                    //.data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<UserInfo>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

    @PostMapping("/logout")
    public ApiRespDTO<String> logout(HttpSession session){
        try {

            sessionService.removeSession(session);

            return ApiRespDTO.<String>builder()
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
    public  ResponseEntity<ApiRespDTO<UserInfo>> login(@RequestBody LoginReq loginRequest, HttpSession session){

        try {
            LoginRespVo responseData = authServiceImpl.getUserInfo(loginRequest, session);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + responseData.getToken());

            return ResponseEntity.ok().headers(headers)
                    .body(ApiRespDTO.<UserInfo>builder()
                            .data(responseData.getUserInfo())
                            .authToken(responseData.getToken())
                            .build());

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiRespDTO.<UserInfo>builder()
                            .code(StatusCodeEnum.ERR00999.name())
                            .message(StatusCodeEnum.ERR00999.getMessage())
                            .build());
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
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

}
