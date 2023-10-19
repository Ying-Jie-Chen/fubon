package com.fubon.ecplatformapi.token;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class CreateToken {
    /*
    產生令牌
    授權需將儲存資訊組成字串 (參照下列 授權令牌內儲存資訊字串內容)後，以AES256加密演算法進行動密後，以Base64進行編碼產生授權令牌。
    簽章作法：sessionId+empNo+系統時間timestamp (需與授權令牌內佔存資訊字串內容的系統時間timestamp相同)。
    組合後以SHA-256將組合字串進行雜湊計算產生簽章。
    */
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    public static String createToken(String sessionId, String empNo, Long timestamp) throws Exception {

        String signature = SHA256Hash(sessionId + empNo + timestamp);

        String token = sessionId + "|" + empNo + "|" + timestamp + "|" + signature;

        SecretKey secretKey = generateAES256Key();

        String encryptedToken = encrypt(token, secretKey);
        String decryptedToken = decrypt(encryptedToken, secretKey);

        log.info("AES256加密後，以Base64進行編碼產生授權令牌: " + encryptedToken);
        log.info("解碼: " + decryptedToken);

        return encryptedToken;
    }

    public static SecretKey generateAES256Key() throws Exception {
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
