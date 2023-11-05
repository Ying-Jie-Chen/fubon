package com.fubon.ecplatformapi.helper;

import com.fubon.ecplatformapi.SessionManager;
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
public class SessionHelper {

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
    public static Object getValueByAttribute(String sessionId, SessionAttribute attribute) {
        HttpSession session = SessionManager.getSessionById(sessionId);
        Object value = session.getAttribute(attribute.name());
        //log.info(attribute + ": " + value);
        return value;
    }

    private static String findSessionFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION-ID".equals(cookie.getName())) { return cookie.getValue(); }
            }
        }
        return null;
    }

}
