package com.fubon.ecplatformapi.controller.test;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import com.fubon.ecplatformapi.repository.TokenRepository;
import com.fubon.ecplatformapi.service.SsoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class SsoController {
    @Autowired
    SsoService ssoService;
    @Autowired
    TokenRepository tokenRepository;

    @GetMapping("/getSSOToken")
    public ApiRespDTO<String> getSSOToken(HttpServletRequest request){
        try {
            String sessionId = SessionHelper.getSessionID(request);


            String ssoToken = ssoService.getSSOToken(sessionId);

            return ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .authToken(tokenRepository.findLatestToken().getToken())
                    .data(ssoToken)
                    .build();

        }catch (Exception e){

            log.error(e.getMessage());
            return  ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

    @PostMapping("/loginSSO")
    public ApiRespDTO<GetUserInfoVo> SSOLogin(@RequestBody SsoReqDTO ssoReq) {
        try {

            ssoService.verifySSOLogin(ssoReq);

            return ApiRespDTO.<GetUserInfoVo>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    //.data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<GetUserInfoVo>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION-ID".equals(cookie.getName())) {return cookie.getValue();}
            }

        }
        return null;
    }

}
