package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.entity.Token;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void saveAuthToken(String authToken);
    String generateToken(String sessionId, String empNo, long timestamp) throws Exception;
    boolean isTokenValid(Token token, HttpServletRequest request) throws Exception;
    void updateToken(Token oldToken) throws Exception;

}
