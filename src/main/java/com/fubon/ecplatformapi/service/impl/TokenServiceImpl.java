package com.fubon.ecplatformapi.service.impl;


import com.fubon.ecplatformapi.SessionManager;
import com.fubon.ecplatformapi.service.TokenService;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.model.entity.Token;
import com.fubon.ecplatformapi.properties.TokenProperties;
import com.fubon.ecplatformapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenProperties tokenProperties;
    @Autowired
    TokenRepository tokenRepository;
    private final SecretKey secretKey;
    @Autowired
    public TokenServiceImpl(SecretKey AESKey) {
        this.secretKey = AESKey;
    }

    @Override
    public void saveAuthToken(String authToken) {
        Token token = Token.builder()
                .token(authToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     *  生成 Token
     */
    @Override
    public String generateToken(String sessionId, String empNo, long timestamp) throws Exception {
        String signature = SHA256Hash(sessionId + empNo + timestamp);
        String tokenContent = sessionId + "|" + empNo + "|" + timestamp + "|" + signature;
        return encrypt(tokenContent, secretKey);
    }

    /**
     * 驗證 Token
     */
    @Override
    public boolean isTokenValid(Token token, HttpServletRequest request){

        try{
            String sessionId = SessionHelper.getSessionID(request);

            String[] tokenParts = decrypt(token.getToken(), secretKey).split("\\|");

            if (tokenParts.length != 4 || !tokenParts[0].equals(sessionId)) {
                token.setRevoked(true);
            }

            Object value = SessionHelper.getAllValue(sessionId);
            //log.info("value: " + value);

            if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
                token.setRevoked(true);
                SessionManager.removeSession(sessionId);
                //sessionService.removeSession(session.getId());
            }

            long decryptedTimestamp = Long.parseLong(tokenParts[2]);
            long currentTimestamp = System.currentTimeMillis();
            long tokenAge = currentTimestamp - decryptedTimestamp;
            Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

            if (tokenAge > tokenExpirationTime.toMillis()) {
                token.setExpired(true);
                SessionManager.removeSession(sessionId);
            }

            if (token.getRevoked() || token.getExpired()){
                log.error("Token has expired or revoked");
                return false;
            }
            return true;

        } catch (Exception e){
            log.error("Token驗證失敗: " + e.getMessage());
            return false;
        }

    }

    /**
     * 更新 Token
     */
    @Override
    public void updateToken(Token oldToken) throws Exception {
        oldToken.setRevoked(true);
        oldToken.setExpired(true);
        tokenRepository.save(oldToken);

        String[] tokenParts = decrypt(oldToken.getToken(), secretKey).split("\\|");
        long currentTimestamp = System.currentTimeMillis();

        Token newToken = Token.builder()
                .token(generateToken(tokenParts[0], tokenParts[1],currentTimestamp))
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    private static boolean validateSignature(String sessionId, String empNo, long timestamp, String signature) throws Exception {
        String computedSignature = SHA256Hash(sessionId + empNo + timestamp);
        return computedSignature.equals(signature);
    }

    private static String SHA256Hash(String data) throws NoSuchAlgorithmException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

}
