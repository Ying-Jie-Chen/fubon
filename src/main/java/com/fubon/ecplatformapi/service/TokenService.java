package com.fubon.ecplatformapi.service;


import com.fubon.ecplatformapi.exception.TokenValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void saveAuthToken(HttpSession session, String authToken);
    HttpSession getSession(String authToken) throws TokenValidationException;
    String generateToken(String sessionId, String empNo, long timestamp) throws Exception;
    boolean isTokenValid(String token) throws Exception;
    void updateToken(HttpServletRequest request, String oldToken) throws Exception;
}
