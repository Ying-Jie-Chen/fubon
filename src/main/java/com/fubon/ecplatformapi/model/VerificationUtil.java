package com.fubon.ecplatformapi.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;
@Service
public class VerificationUtil {
    private int CODE_SIZE = 4;
    private int WIDTH = 165;
    private int HEIGHT = 45;
    private int LINES = 30;
    private int FONT_SIZE = 40;
    private static final Random random = new Random();
    private String randomString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ";
    private final String sessionKey = "JCCODE";


    // 字體
    Font font = new Font("Times New Roman", Font.BOLD + Font.ITALIC, FONT_SIZE);

    // 顏色
    private static Color getRandomColor() {
//        int fc, int bc
//        fc = Math.min(fc, 255);
//        bc = Math.min(bc, 255);
//
//        int r = fc + random.nextInt(bc - fc - 16);
//        int g = fc + random.nextInt(bc - fc - 14);
//        int b = fc + random.nextInt(bc - fc - 12);
//
//        return new Color(r, g, b);
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }

//    //干扰线的绘制
//    private void drawLine(Graphics g) {
//        int x = random.nextInt(WIDTH);
//        int y = random.nextInt(HEIGHT);
//        int xl = random.nextInt(20);
//        int yl = random.nextInt(10);
//        g.drawLine(x, y, x + xl, y + yl);
//
//    }



    //随机字符的获取
    private  String getRandomString(int num){
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    //字符串的绘制
    private String drawString(Graphics g, String randomStr, int i) {
        g.setFont(font);
//        g.setColor(getRandomColor(108, 190));
        g.setColor(getRandomColor());
        //System.out.println(random.nextInt(randomString.length()));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    //生成随机图片的base64编码字符串
    public String getRandomCodeBase64(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, WIDTH, HEIGHT);
//        g.setColor(getRandomColor(105, 189));
        g.setColor(getRandomColor());
        g.setFont(font);
        // 干擾線
        for (int i = 0; i < LINES; i++) {
//            drawLine(g);
            g.setColor(getRandomColor());
            g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
                       random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }

        //隨機字串驗證碼
        String randomStr = "";
        for (int i = 0; i < CODE_SIZE; i++) {
            randomStr = drawString(g, randomStr, i);
        }
        System.out.println("驗證碼：" + randomStr);
        g.dispose();
        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, randomStr);
        String base64String = "";
        try {
            //  直接返回图片
            //  ImageIO.write(image, "PNG", response.getOutputStream());
            //返回 base64
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);

            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            base64String = encoder.encodeToString(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64String;
    }

}
