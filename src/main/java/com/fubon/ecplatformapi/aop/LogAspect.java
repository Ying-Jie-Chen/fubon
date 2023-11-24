package com.fubon.ecplatformapi.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.fubon.ecplatformapi..*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            logger.info("Method {} executed in {} ms", methodName, executionTime);
        }
    }

    @Around("execution(* com.fubon.ecplatformapi.service.AuthService.callFubonService(..))")
    public Object logWebClientExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            logger.info("WebClient call took {} ms", executionTime);
        }
    }

    @Around("execution(* com.fubon.ecplatformapi.repository.*.*(..))")
    public Object logDatabaseExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String methodName = joinPoint.getSignature().toShortString();

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            long executionTime = stopWatch.getLastTaskTimeMillis();

            logger.info("Database operation in method {} took {} ms", methodName, executionTime);
        }
    }

    @Around("execution(* com.fubon.ecplatformapi.controller.*.*(..))")
    public Object logHttpResponseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            logHttpResponseInfo(methodName, executionTime);
        }
    }

    private void logHttpResponseInfo(String methodName, long executionTime) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        int statusCode = request.getAttribute("javax.servlet.error.status_code") != null ?
                (int) request.getAttribute("javax.servlet.error.status_code") : 200;

        logger.info("HTTP response for method {} (Status Code: {}, Execution Time: {} ms)", methodName, statusCode, executionTime);
    }
}
