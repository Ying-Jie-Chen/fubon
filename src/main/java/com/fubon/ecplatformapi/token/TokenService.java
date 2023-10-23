package com.fubon.ecplatformapi.token;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public interface TokenService {
    String generateToken(String sessionId, String empNo, long timestamp) throws Exception;
    boolean isTokenValid(Token token) throws Exception;
    Token updateToken(Token oldToken) throws Exception;
    SecretKey generateAES256Key() throws Exception;

}
