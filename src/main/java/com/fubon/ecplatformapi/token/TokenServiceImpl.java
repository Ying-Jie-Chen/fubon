package com.fubon.ecplatformapi.token;


import com.fubon.ecplatformapi.config.SessionConfig;
import com.fubon.ecplatformapi.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenProperties tokenProperties;
    @Autowired
    SessionService sessionService;
    @Autowired
    HttpSession httpSession;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA-256";

    /**
     *  生成 Token
     *
     */
    @Override
    public Token generateToken(String sessionId, String empNo, long timestamp, SecretKey AESKey) throws Exception {

        String signature = SHA256Hash(sessionId + empNo + timestamp);
        String tokenContent = sessionId + "|" + empNo + "|" + timestamp + "|" + signature;

        Token token = new Token();
        token.setToken(encrypt(tokenContent, AESKey));
        token.setRevoked(false);
        token.setExpired(false);

        return token;
    }

    /**
     *  驗證 Token
     *
     */
    @Override
    public String validateToken(Token token, SecretKey AESKey) throws Exception {

        String decryptedToken = decrypt(token.getToken(), AESKey);
        String[] tokenParts = decryptedToken.split("\\|");

        if (tokenParts.length != 4) {
            return "Invalid token format";
        }
        log.info("http session ID: " + httpSession.getId());
        log.info(" tokenParts[0]: " + tokenParts[0]);
        log.info("在Session中找不到對應的資訊，則返回錯誤訊息");
//        if (!sessionService.getSessionInfo(tokenParts[0])) {
//            return "Session ID does not match or doesn't exist in Session";
//        }

        log.info("驗證簽章#Start");
        if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
            token.setRevoked(true);
            return "Invalid signature";
        }

        long decryptedTimestamp = Long.parseLong(tokenParts[2]);
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long tokenAge = currentTimestamp - decryptedTimestamp;
        Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

        log.info("驗證令牌是否過期#Start");
        if (tokenAge > tokenExpirationTime.toMillis()) {
            token.setExpired(true);
            //sessionService.removeSession(tokenParts[0]);
            return "Token has expired";
        }


        return "Token is valid";
    }

    @Override
    public Token updateToken(Token oldToken, SecretKey AESKey) throws Exception {

        String decryptedToken = decrypt(oldToken.getToken(), AESKey);
        String[] tokenParts = decryptedToken.split("\\|");

        String sessionId = tokenParts[0];
        String empNo = tokenParts[1];

        if (sessionId == null) {
            throw new IllegalArgumentException("Invalid old token");
        }

        long currentTimestamp = System.currentTimeMillis() / 1000;

        Token newToken = generateToken(sessionId, empNo, currentTimestamp, AESKey);
        oldToken.setRevoked(true);

        log.info("Is Revoked?: " + oldToken.isRevoked());

        return newToken;
    }

    @Override
    public SecretKey generateAES256Key() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
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
            MessageDigest digest = MessageDigest.getInstance(SIGNATURE_ALGORITHM);
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
