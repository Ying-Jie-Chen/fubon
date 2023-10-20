package com.fubon.ecplatformapi.token;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public interface TokenService {
    Token generateToken(String sessionId, String empNo, long timestamp, SecretKey AESKey) throws Exception;
    String validateToken(Token token, SecretKey AESKey) throws Exception;
    Token updateToken(Token oldToken, SecretKey AESKey) throws Exception;
    SecretKey generateAES256Key() throws Exception;

}
