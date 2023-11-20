package com.fubon.ecplatformapi.aop;

import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
@Component
@Aspect
public class HeaderAspect {
    @Autowired
    TokenService tokenService;
    // || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.getSSOToken(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.SSOLogin(..))
    @Pointcut("execution(* com.fubon.ecplatformapi.controller.other.*.*(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.getSSOToken(..)) || execution(* com.fubon.ecplatformapi.controller.auth.AuthController.SSOLogin(..)) ")
    private void headerValidation() { }

    @Around("headerValidation()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final String authHeader = request.getHeader("Authorization");
        HttpSession session = request.getSession();

        try {

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")) { return errorResponse(); }

            String token = authHeader.substring(7);
            //String storedToken = (String) session.getAttribute("AUTH_TOKEN");
            //log.info("Auth Token: " + storedToken);
            tokenService.getAuthToken(token);

            if(token.equals(token) && tokenService.isTokenValid(token)) {
                tokenService.updateToken(session, token);
                return joinPoint.proceed();
            } else {
                System.out.println("Token isn't valid.");
                return errorResponse();
            }

        } catch (Throwable e) {
            System.out.println("Error: " + e.getMessage());
            log.error("Error: " + e.getMessage());
            return errorResponse();
        }
    }

    private ApiRespDTO<?> errorResponse() {
        return ApiRespDTO.builder()
                .code(StatusCodeEnum.ERR00002.getCode())
                .message(StatusCodeEnum.ERR00002.getMessage())
                .build();
    }

}

