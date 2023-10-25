package com.fubon.ecplatformapi.token;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    String generateToken(String sessionId, String empNo, long timestamp) throws Exception;
    boolean isTokenValid(Token token, HttpSession session) throws Exception;
    String updateToken(Token oldToken) throws Exception;

}
