package com.fubon.ecplatformapi.aop;

import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.entity.Token;
import com.fubon.ecplatformapi.repository.TokenRepository;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    TokenRepository tokenRepository;

    @Pointcut("execution(* com.fubon.ecplatformapi.controller.PolicyController.*(..))")
    private void headerValidation() { }

    @Around("headerValidation()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final String authHeader = request.getHeader("Authorization");
        HttpSession session = request.getSession();
        try {

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer")) {
                return ApiRespDTO.builder()
                        .code("UNAUTHORIZED")
                        .message("Invalid or missing token")
                        .build();
            }

            String token = authHeader.substring(7);
            Token storedToken = tokenRepository.findByToken(token).orElse(null);
            log.info("storedToken: " + storedToken);

            if (tokenService.isTokenValid(storedToken, session)) {
                tokenService.updateToken(storedToken);
                return joinPoint.proceed();
            }else {
                return ApiRespDTO.builder()
                        .code("UNAUTHORIZED")
                        .message("Unauthorized: Invalid token")
                        .build();
            }

        } catch (Throwable e) {
            log.error("Error: " + e.getMessage());
            return ApiRespDTO.builder()
                    .code("INTERNAL_SERVER_ERROR")
                    .message("Internal Server Error")
                    .build();
        }
    }
}
