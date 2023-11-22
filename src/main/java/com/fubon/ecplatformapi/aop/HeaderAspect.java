package com.fubon.ecplatformapi.aop;

import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.exception.TokenValidationException;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.TokenService;
import com.microsoft.sqlserver.jdbc.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Aspect
public class HeaderAspect extends SessionController {
    @Autowired
    TokenService tokenService;
    @Pointcut("execution(* com.fubon.ecplatformapi.controller.other.*.*(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.logout(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.getSSOToken(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.SSOLogin(..))")
    private void headerValidation() { }

    @Around("headerValidation()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final String authHeader = request.getHeader("Authorization");

        try {
            log.debug("Entering HeaderAspect.around ");

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")) {
                log.debug("Invalid or missing Authorization header. Returning error response.");
                return tokenErrorResp("Invalid or missing Authorization header");
            }

            String token = authHeader.substring(7);
            log.debug("User provided Token: " + token);

            if (tokenService.isTokenValid(token)) {
                log.debug("取得登入的 HttpSession");
                HttpSession session = tokenService.getSession(token);

                if (session != null) {
                    storeSessionAndAuthToken(request, session, token);
                    tokenService.updateToken(request, token);
                    log.debug("Proceeding with JoinPoint execution.");
                    return joinPoint.proceed();
                } else {
                    log.debug("Session not found for authToken: " + token);
                    return tokenErrorResp("Session not found for authToken");
                }
            } else {
                return tokenErrorResp("Token is not valid");
            }
        }catch (TokenValidationException e){
            log.error("** Error during Valid Token: " + e.getMessage());
            return systemErrorResp(e.getMessage());

        }catch (CustomException e){
            log.error("** Error during JoinPoint CustomExecution: " + e.getMessage());
            return systemErrorResp(e.getMessage());

        }catch (Exception e) {
            log.error("** Error during JoinPoint Exception: " + e.getMessage());
            return systemErrorResp(e.getMessage());

        }catch (Throwable e) {
            log.error("** Error during JoinPoint Throwable: " + e.getMessage());
            System.out.println();
            return systemErrorResp(e.getMessage());
        }
    }

    private void storeSessionAndAuthToken(HttpServletRequest request, HttpSession session, String token) {
        request.setAttribute("SESSION_ID", session.getId());
        request.setAttribute("AUTH_TOKEN", token);
        log.debug("儲存 Session ID 到 Request: " + session.getId() + " 儲存 AuthToken 到 Request:" + token);
    }

    private ApiRespDTO<?> tokenErrorResp(String msg) {
        return ApiRespDTO.builder()
                .code(StatusCodeEnum.ERR00002.getCode())
                .message(StatusCodeEnum.ERR00002.getMessage() + " : " + msg)
                //.authToken(getAuthToken())
                .build();
    }

}

