package com.fubon.ecplatformapi.NoUse.captcha;

import com.fubon.ecplatformapi.service.impl.SessionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Slf4j
@Service
public class CaptchaUtil {
    @Autowired
    SessionServiceImpl sessionService;

    private final int CODE_SIZE = 4;
    private final int WIDTH = 165;
    private final int HEIGHT = 45;
    private final int LINES = 30;
    private static final int FONT_SIZE = 40;
    private static final String RANDOM_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ";
    private static final Font FONT = new Font("Times New Roman", Font.BOLD + Font.ITALIC, FONT_SIZE);;
    private static final Random RANDOM = new Random();


    private static Color getRandomColor() {
        Random ran = new Random();

        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    /** 生成隨機驗證碼字串
     *
     */
    private static String getRandomString(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Try again!");
        }
        num = Math.max(num, 0);
        num = Math.min(num, RANDOM_STRING.length());

        return String.valueOf(RANDOM_STRING.charAt(RANDOM.nextInt(num)));
    }

    /** 在圖片上繪製驗證碼
     *
     */
    private static String drawString(Graphics g, String randomStr, int i) {
        g.setFont(FONT);
        g.setColor(getRandomColor());
        String rand = getRandomString(RANDOM.nextInt(RANDOM_STRING.length()));
        randomStr += rand;
        g.translate(RANDOM.nextInt(3), RANDOM.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    /** 產生驗證碼圖片，傳回未編碼的驗證碼字串到session
     *
     */
    public byte[] getRandomCode(BufferedImage image) {
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(getRandomColor());
        g.setFont(FONT);

        //干擾線
        for (int i = 0; i < LINES; i++) {
            g.setColor(getRandomColor());
            g.drawLine(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT),
                    RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
        }

        // 隨機位數驗證碼
        String randomStr = "";
        for (int i = 0; i < CODE_SIZE; i++) {
            randomStr = drawString(g, randomStr, i);
        }

        // 將驗證碼字串儲存在會話中
        //log.info("驗證碼：" + randomStr);
        sessionService.saveSession(randomStr);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

    /**
     * 產生隨機圖片的 base64 編碼字串
     *
     */
    public String generateCaptchaBase64() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        byte[] captcha = getRandomCode(image);

        String base64String = "";
        Base64.Encoder encoder = Base64.getEncoder();
        base64String = encoder.encodeToString(captcha);

        log.info("驗證碼 Base64 : " + base64String);

        return base64String;
    }
}
