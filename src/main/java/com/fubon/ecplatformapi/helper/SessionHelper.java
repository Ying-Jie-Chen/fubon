package com.fubon.ecplatformapi.helper;

import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.SessionAttribute;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SessionHelper extends SessionController {

    /**
     * 取得 Session ID
     */
    public static String getSessionID(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String sessionId = findSessionFromCookies(cookies);
        if (sessionId != null) {
            if (SessionManager.getSessionById(sessionId) != null) {
                //log.info(sessionId);
                return sessionId;
            } else { log.warn("Session不存在"); }
        }
        return null;
    }

    /**
     * 取得儲存在Session中的All Value
     */
    public static Map<String, Object> getAllValue(String sessionId) {
        HttpSession session = SessionManager.getSessionById(sessionId);

        Map<String, Object> values = new HashMap<>();
        for (SessionAttribute attribute : SessionAttribute.values()) {
            Object value = session.getAttribute(attribute.name());
            values.put(attribute.name(), value);
            //log.info(attribute + ": " + value);
        }
        return values;
    }

    /**
     * 取得儲存在Session中的特定Value
     */
    public static String getValueByAttribute(String sessionId, SessionAttribute attribute) {
            HttpSession session = SessionManager.getSessionById(sessionId);
            String value = session.getAttribute(attribute.name()).toString();
            //log.info(attribute + ": " + value);
            return value;
    }

    private static String findSessionFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION-ID".equals(cookie.getName())) {
                    //log.info("SESSION-ID="+cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
