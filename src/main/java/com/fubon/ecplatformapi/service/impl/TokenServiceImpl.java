package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.exception.TokenValidationException;
import com.fubon.ecplatformapi.properties.TokenProperties;
import com.fubon.ecplatformapi.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
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
public class TokenServiceImpl implements TokenService {
    private final Map<String, String> authTokenMap = new ConcurrentHashMap<String, String>();
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
    public void saveAuthToken(HttpSession session, String authToken) {
        log.debug("Saving Auth Token for session: " + session.getId() + ", AUTH_TOKEN: " + authToken);
        authTokenMap.put(authToken, session.getId());
        log.debug("Session and Auth Token saved successfully.");
    }

    @Override
    public HttpSession getSession(String authToken) throws TokenValidationException{
        log.debug("用 AuthToken 拿取登入的 HttpSession #Start : " + authToken);

        String sessionId = authTokenMap.get(authToken);
        log.debug("從 AuthTokenMap 取得 AuthToken 對應的 SessionID #Passed ");

        if (sessionId != null) {
            HttpSession session = SessionManager.getSessionById(sessionId);
            log.debug(" 從 SessionManager 找到 SessionID 回傳登入的 HttpSession #Passed");

            if (session != null) {
                log.debug("Found session ID from authTokenMap: " + sessionId + " for authToken: " + authToken);
                return session;
            } else {
                log.debug("Session is null");
                throw new TokenValidationException("Session不存在或已經清除 ex.登出");
            }
        } else {
            log.debug("SessionID is null");
            throw new TokenValidationException("授權令牌重複輸入");
        }
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
    public boolean isTokenValid(String token) throws TokenValidationException {
        log.debug("Validating Token for: " + token);

        try{
            String[] tokenParts = decrypt(token, secretKey).split("\\|");
            String sessionID = tokenParts[0];
            if (tokenParts.length != 4 ) {
                log.debug("Incorrect Token length");
                throw new TokenValidationException("Incorrect Token length");
            }

            //SessionHelper.getAllValue();

            if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
                log.debug("Signature validation failed");
                SessionManager.removeSession(sessionID);
                throw new TokenValidationException("Signature validation failed");
            }

            long decryptedTimestamp = Long.parseLong(tokenParts[2]);
            long currentTimestamp = System.currentTimeMillis();
            long tokenAge = currentTimestamp - decryptedTimestamp;
            Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

            log.debug("Token Age: " + tokenAge + " ms, Expiration Time: " + tokenExpirationTime.toMillis() + " ms");

            if (tokenAge > tokenExpirationTime.toMillis()) {
                log.debug("Token has expired");
                SessionManager.removeSession(sessionID);
                throw new TokenValidationException("Token has expired");
            }

            log.debug("Token validation passed");
            return true;

        } catch (BadPaddingException e) {
            log.error("Bad padding exception: " + e.getMessage());
            throw new TokenValidationException("令牌已被篡改或在傳輸過程中損壞");

        } catch (Exception e){
            log.error("Token validation failed: " + e.getMessage());
            throw new TokenValidationException(e.getMessage());
        }
    }

    /**
     * 更新 Token
     */
    @Override
    public void updateToken(HttpServletRequest request, String oldToken) throws Exception {
        log.debug("Updating Token for old Token");
        String[] tokenParts = decrypt(oldToken, secretKey).split("\\|");
        long currentTimestamp = System.currentTimeMillis();

        String newToken = generateToken(tokenParts[0], tokenParts[1],currentTimestamp);
        log.debug("Generated new token: " + newToken);
        authTokenMap.remove(oldToken);
        authTokenMap.put(newToken, tokenParts[0]);
        log.debug("sessionID: " + tokenParts[0]);

        request.setAttribute("AUTH_TOKEN", newToken);

        log.debug("New Token : " + request.getAttribute("AUTH_TOKEN"));
        log.debug("Session and Auth Token saved successfully.");

        //log.debug("New AUTH_TOKEN saved in request: " + request.getAttribute("AUTH_TOKEN"));
        log.debug("Token updating passed");
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
