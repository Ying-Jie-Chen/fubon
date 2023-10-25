package com.fubon.ecplatformapi.token;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SessionHelper {

    public static Map<String, Object> getAllValue(HttpSession session) {
        log.info("取得儲存在Session中的All Value#Start");

        Map<String, Object> values = new HashMap<>();

        for (SessionAttribute attribute : SessionAttribute.values()) {
            Object value = session.getAttribute(attribute.name());
            values.put(attribute.name(), value);
            //log.info(attribute + ": " + value);
        }
        return values;
    }

    public static Object getValueByAttribute(HttpSession session, SessionAttribute attribute) {
        log.info("取得儲存在Session中的特定Value#Start");

        Object value = session.getAttribute(attribute.getAttributeName());
        log.info(attribute.getAttributeName() + ": " + value);
        return value;
    }

}
