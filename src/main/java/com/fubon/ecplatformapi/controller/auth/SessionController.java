package com.fubon.ecplatformapi.controller.auth;


import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Controller
public abstract class SessionController {
    private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public static String sessionID() {
        log.debug("從當前請求的中獲取 sessionID(): ");
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String sessionId = (String) request.getAttribute("SESSION_ID");
        //log.debug("Session Controller SESSION ID: " + sessionId);
        if (sessionId != null && SessionManager.getSessionById(sessionId) != null) {
            //log.debug("Session found. AUTH_TOKEN: " + session.getAttribute("AUTH_TOKEN"));
            log.debug("Session ID found: " + sessionId);
            return sessionId;
        } else {
            log.debug("Session ID not found in Session Manager or session is not valid.");
        }
        return null;
    }

    public static String getAuthToken() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String authToken = (String) request.getAttribute("AUTH_TOKEN");
        log.debug("getAuthToken() : " + authToken);
        return authToken;
    }

    public <T> ApiRespDTO<T> successApiResp(T data){
        return ApiRespDTO.<T>builder()
                .code(StatusCodeEnum.SUCCESS.getCode())
                .message(StatusCodeEnum.SUCCESS.getMessage())
                .authToken(getAuthToken())
                .data(data)
                .build();
    }

    public <T> ApiRespDTO<T> systemErrorResp(){
        return ApiRespDTO.<T>builder()
                .code(StatusCodeEnum.ERR00999.name())
                .message(StatusCodeEnum.ERR00999.getMessage())
                .authToken(getAuthToken())
                .build();
    }

    public <T> ApiRespDTO<T> systemErrorResp(String message){
        return ApiRespDTO.<T>builder()
                .code(StatusCodeEnum.ERR00999.name())
                .message(StatusCodeEnum.ERR00999.getMessage() + " : " + message)
                .authToken(getAuthToken())
                .build();
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