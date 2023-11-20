package com.fubon.ecplatformapi.controller.auth;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Controller
public abstract class SessionController {

    public static String sessionID() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return SessionHelper.getSessionID(request);
    }

    public static String getAuthToken() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
            return (String) session.getAttribute("AUTH_TOKEN");
        }
        return null;
    }

    public <T> ApiRespDTO<T> successApiResp(T data){
        return result(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage(), data);
    }

    public <T> ApiRespDTO<T> errorApiResp(){
        return result(StatusCodeEnum.ERR00999.name(), StatusCodeEnum.ERR00999.getMessage(), null);
    }

    public  <T> ApiRespDTO<T> result(String statusCode, String msg, T data) {
        return ApiRespDTO.<T>builder()
                .code(statusCode)
                .message(msg)
                .authToken(getAuthToken())
                .data(data)
                .build();
    }
}