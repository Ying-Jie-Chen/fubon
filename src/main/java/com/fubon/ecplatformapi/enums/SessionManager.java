package com.fubon.ecplatformapi.enums;

import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.session.MapSession;
import org.springframework.session.Session;

import java.util.Collections;
import java.util.List;
@Getter
@AllArgsConstructor
public enum SessionManager {
    IDENTITY("identity"),

    EMP_NO("empNo"),

    EMP_NAME("empName"),

    FBID("fbid"),

    XREF_INFOS("xrefInfos");

    private final String attributeName;

    public static void setAttribute(Session session, SessionManager attribute, Object value) {
        session.setAttribute(attribute.getAttributeName(), value);
    }

    public static <T> T getAttribute(Session session, SessionManager attribute, Class<T> type) {
        Object value = session.getAttribute(attribute.getAttributeName());
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        throw new IllegalArgumentException("Attribute type mismatch for " + attribute);
    }

    public static Object getAttribute(Session session, SessionManager attribute) {
        return session.getAttribute(attribute.getAttributeName());
    }

    public static void removeAttribute(Session session, SessionManager attribute) {
        session.removeAttribute(attribute.getAttributeName());
    }

    public static void setXrefInfoAttribute(Session session, List<UserInfo.XrefInfo> xrefInfoList) {
        setAttribute(session, XREF_INFOS, xrefInfoList);
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
