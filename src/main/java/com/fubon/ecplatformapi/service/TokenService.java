package com.fubon.ecplatformapi.service;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void saveAuthToken(HttpServletResponse response, HttpSession session, String authToken);

    void getAuthToken(String authToken);

    String generateToken(String sessionId, String empNo, long timestamp) throws Exception;
    boolean isTokenValid(String token) throws Exception;
    void updateToken(HttpSession session, String oldToken) throws Exception;

}
