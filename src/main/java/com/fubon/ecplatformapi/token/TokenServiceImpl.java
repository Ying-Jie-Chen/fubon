package com.fubon.ecplatformapi.token;


import com.fubon.ecplatformapi.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
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
    @Autowired
    SessionService sessionService;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA-256";

    private final SecretKey secretKey;
    @Autowired
    public TokenServiceImpl(SecretKey AESKey) {
        this.secretKey = AESKey;
    }


    /**
     *  生成 Token
     *
     */
    @Override
    public String generateToken(String sessionId, String empNo, long timestamp) throws Exception {
        log.info("Generate Token #Start");

        String signature = SHA256Hash(sessionId + empNo + timestamp);
        String tokenContent = sessionId + "|" + empNo + "|" + timestamp + "|" + signature;
        //log.info("Token 有效時間：" + tokenProperties.getExpirationMinutes());
        String authToken = encrypt(tokenContent, secretKey);
        log.info("Generate Token: " + authToken);
        return authToken;
    }


    /**
     * 驗證 Token
     */
    @Override
    public boolean isTokenValid(Token token, HttpSession session) throws Exception {
        log.info("驗證Token #Start");
        log.info("token: " + token + "AESKey: " + secretKey);

        String[] tokenParts = decrypt(token.getToken(), secretKey).split("\\|");

        if (tokenParts.length != 4) {
            log.error("Invalid token format");
            return false;
        }

        log.info("token praise Session ID: " + tokenParts[0]);
        log.info("session id: " + session.getId());
        log.info("在Session中找不到對應的資訊，則返回錯誤訊息");
        if (!sessionService.getSessionInfo(session)) {
            log.error("Session ID does not match or doesn't exist in Session");
            return false;
        }

        log.info("驗證簽章#Start");
        if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
            token.setRevoked(true);
            log.error("Invalid signature");
            return false;
        }

        long decryptedTimestamp = Long.parseLong(tokenParts[2]);
        long currentTimestamp = System.currentTimeMillis();
        long tokenAge = currentTimestamp - decryptedTimestamp;
        Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

        log.info("驗證令牌是否過期#Start");
        if (tokenAge > tokenExpirationTime.toMillis()) {
            token.setExpired(true);
            sessionService.removeSession(session);
            log.error("Token has expired");
            return false;
        }
        log.info("Token驗證成功 #End");
        token.setRevoked(true);
        token.setExpired(true);
        tokenRepository.save(token);
        // 更新 token 過期時間
        //tokenProperties.setExpirationMinutes(tokenExpirationTime);
        //log.info("Token 過期時間: " + tokenExpirationTime);

        // 產生新 token
        Token newToken = Token.builder()
                .token(generateToken(tokenParts[0], tokenParts[1],currentTimestamp))
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
        log.info("New Token: " + newToken);
        return true;
    }

    @Override
    public Token updateToken(Token oldToken) throws Exception {

        String decryptedToken = decrypt(oldToken.getToken(), secretKey);
        String[] tokenParts = decryptedToken.split("\\|");

        String sessionId = tokenParts[0];
        String empNo = tokenParts[1];

        if (sessionId == null) {
            throw new IllegalArgumentException("Invalid old token");
        }

        long currentTimestamp = System.currentTimeMillis();

        String newToken = generateToken(sessionId, empNo, currentTimestamp);
        //oldToken.setRevoked(true);

        //log.info("Is Revoked?: " + oldToken.isRevoked());

        //return newToken;
        return null;
    }

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
