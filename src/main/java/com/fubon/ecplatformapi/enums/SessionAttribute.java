package com.fubon.ecplatformapi.enums;

import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.session.Session;

import java.util.Collections;
import java.util.List;
@Getter
@AllArgsConstructor
public enum SessionAttribute {
    IDENTITY("identity"),

    EMP_NO("empNo"),

    EMP_NAME("empName"),

    FBID("fbid"),

    UNION_NUM("unionNum"),

    ADMIN_NUM("adminNum"),

    EMAIL("email"),

    XREF_INFOS("xrefInfos");

    private final String attributeName;

    public static void setAttribute(Session session, SessionAttribute attribute, Object value) {
        session.setAttribute(attribute.getAttributeName(), value);
    }

    public static Object getAttribute(Session session, SessionAttribute attribute) {
        return session.getAttribute(attribute.getAttributeName());
    }

    public static void removeAttribute(Session session, SessionAttribute attribute) {
        session.removeAttribute(attribute.getAttributeName());
    }

    public static List<UserInfo.XrefInfo> getXrefInfoAttribute(Session session) {
        Object attribute = getAttribute(session, XREF_INFOS);
        if (attribute instanceof List) {
            return (List<UserInfo.XrefInfo>) attribute;
        } else {
            return Collections.emptyList();
        }
    }

}
