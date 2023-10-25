package com.fubon.ecplatformapi.token;


import com.fubon.ecplatformapi.service.SessionService;
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

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenProperties tokenProperties;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    SessionService sessionService;
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
        String authToken = encrypt(tokenContent, secretKey);
        log.info("Generate Token: " + authToken);
        return authToken;
    }

    /**
     * 驗證 Token
     */
    @Override
    public boolean isTokenValid(Token token, HttpSession session){
        log.info("驗證Token #Start");
        log.info("token: " + token + "AESKey: " + secretKey);
        try{

            String[] tokenParts = decrypt(token.getToken(), secretKey).split("\\|");

            if (tokenParts.length != 4) {
                //log.error("Invalid token format");
                token.setRevoked(true);
            }

            Object value = SessionHelper.getAllValue(session);
            log.info("value: " + value);

            if (!tokenParts[0].equals(session.getId())) {
                log.error("在Session中找不到對應的資訊");
                token.setRevoked(true);
            }

            log.info("驗證簽章#Start");
            if (!validateSignature(tokenParts[0], tokenParts[1], Long.parseLong(tokenParts[2]), tokenParts[3])) {
                token.setRevoked(true);
                sessionService.removeSession(session);
                log.error("Invalid signature");
            }

            long decryptedTimestamp = Long.parseLong(tokenParts[2]);
            long currentTimestamp = System.currentTimeMillis();
            long tokenAge = currentTimestamp - decryptedTimestamp;
            Duration tokenExpirationTime = tokenProperties.getExpirationMinutes();

            log.info("Token Age: " + tokenAge);

            log.info("驗證令牌是否過期#Start");
            if (tokenAge > tokenExpirationTime.toMillis()) {
                token.setExpired(true);
                sessionService.removeSession(session);
                log.error("Token has expired");
            }

            if (token.getRevoked() || token.getExpired()){
                log.error("Token has expired or revoked");
                return false;
            }

            log.info("Token驗證成功 #End");
            return true;

        }catch (Exception e){
            log.error("Token驗證失敗: " + e.getMessage());
            return false;
        }

    }

    /**
     * 更新 Token
     *
     * @return
     */
    @Override
    public String updateToken(Token oldToken) throws Exception {
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

        log.info("New Token: " + newToken);
        return newToken.getToken();
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
