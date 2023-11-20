package com.fubon.ecplatformapi.service.impl;


import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.service.TokenService;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.properties.TokenProperties;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TokenServiceImpl extends SessionController implements TokenService {
    private final Map<String, HttpSession> authTokenMap = new ConcurrentHashMap<String, HttpSession>();
    @Autowired
    TokenProperties tokenProperties;
    private final SecretKey secretKey;
    @Autowired
    public TokenServiceImpl(SecretKey AESKey) {
        this.secretKey = AESKey;
    }

    /**
     *  儲存 Token
     */
    @Override
    public void saveAuthToken(HttpServletResponse response,HttpSession session, String authToken) {
        authTokenMap.put(authToken, session);
        session.setAttribute("AUTH_TOKEN", authToken);
        SessionManager.saveAuthToken(response, session);
    }

    @Override
    public void getAuthToken(String authToken){
        authTokenMap.get(authToken);
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
    public boolean isTokenValid(String token){

        try{

            String[] tokenParts = decrypt(token, secretKey).split("\\|");

            if (tokenParts.length != 4 || !tokenParts[0].equals(sessionID())) {
                return false;
            }

            Object value = SessionHelper.getAllValue(sessionID());
            //log.info("value: " + value);

            if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
                SessionManager.removeSession(sessionID());
                return false;
            }

            long decryptedTimestamp = Long.parseLong(tokenParts[2]);
            long currentTimestamp = System.currentTimeMillis();
            long tokenAge = currentTimestamp - decryptedTimestamp;
            Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

            if (tokenAge > tokenExpirationTime.toMillis()) {
                SessionManager.removeSession(sessionID());
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
    public void updateToken(HttpSession session, String oldToken) throws Exception {

        String[] tokenParts = decrypt(oldToken, secretKey).split("\\|");
        long currentTimestamp = System.currentTimeMillis();

        String newToken = generateToken(tokenParts[0], tokenParts[1],currentTimestamp);
        session.setAttribute("AUTH_TOKEN", newToken);
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
