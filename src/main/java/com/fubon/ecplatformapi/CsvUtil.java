package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.helper.SessionHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class CsvUtil {

    public String generateCsv(String sessionId) {
        List<SessionAttribute> attributes = new ArrayList<>();
        attributes.add(SessionAttribute.EMP_NO);
        attributes.add(SessionAttribute.EMAIL);
        attributes.add(SessionAttribute.FBID);
        attributes.add(SessionAttribute.EMP_NAME);
        attributes.add(SessionAttribute.ADMIN_NUM);
        attributes.add(SessionAttribute.EMP_NO);

        Map<SessionAttribute, String> sessionData = new HashMap<>();

        for (SessionAttribute attribute : attributes) {
            Object value = SessionHelper.getValueByAttribute(sessionId, attribute);
            sessionData.put(attribute, value.toString());
        }

        List<String> csvData = new ArrayList<>();
        for (SessionAttribute attribute : attributes) {
            String value = sessionData.get(attribute);
            csvData.add(attribute.name());
            csvData.add(value != null ? value : "");
        }

        System.out.println("CSV data: " + String.join(",", csvData));

        return String.join(",", csvData);
    }
}
