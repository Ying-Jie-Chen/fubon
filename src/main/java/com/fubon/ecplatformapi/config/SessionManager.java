package com.fubon.ecplatformapi.config;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SessionManager {

    private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public static void associateSession(HttpSession session, String sessionId) {
        sessionMap.put(sessionId, session);
    }

    public static HttpSession getSessionById(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }


    public static void saveSession(HttpSession session, HttpServletResponse response, LoginRespDTO fbLoginRespDTO){
        associateSession(session, session.getId());
        setSessionAttributes(session.getId(), fbLoginRespDTO);
        saveSessionID(response, session);
    }

    /**
     * 儲存登入者資訊
     *
     */
    public static void saveSessionID(HttpServletResponse response, HttpSession session) {
        Cookie sessionCookie = new Cookie("SESSION-ID", session.getId());
        sessionCookie.setMaxAge(-1);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
    }

    public static void setSessionAttributes(String sessionId, LoginRespDTO dto) {
        HttpSession session = getSessionById(sessionId);
        session.setMaxInactiveInterval(1200);

        LoginRespDTO.UserInfo user = dto.getAny().getUserInfo();
        List<LoginRespDTO.XrefInfo> userXrefInfoList = dto.getAny().getXrefInfo();

        session.setAttribute(String.valueOf(SessionAttribute.IDENTITY), user.getIdentity());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NO), user.getAgent_id());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NAME), user.getAgent_name());
        session.setAttribute(String.valueOf(SessionAttribute.FBID), user.getId());
        session.setAttribute(String.valueOf(SessionAttribute.UNION_NUM), user.getUnion_num());
        session.setAttribute(String.valueOf(SessionAttribute.ADMIN_NUM), user.getAdmin_num());
        session.setAttribute(String.valueOf(SessionAttribute.EMAIL), user.getEmail());
        session.setAttribute(String.valueOf(SessionAttribute.SALES_ID), user.getSales_id());
        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), user);

        List<LoginRespDTO.XrefInfo> xrefInfoList = setXrefInfo(userXrefInfoList);
        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), xrefInfoList);
    }

    public static List<LoginRespDTO.XrefInfo> setXrefInfo(List<LoginRespDTO.XrefInfo> xrefInfoList) {
        return xrefInfoList.stream()
                .map(xref -> LoginRespDTO.XrefInfo.builder()
                        .xref(xref.getXref())
                        .channel(xref.getChannel())
                        .regno(xref.getRegno())
                        .empcname(xref.getEmpcname())
                        .adminSeq(xref.getAdminSeq())
                        .ascCrzSale(xref.getAscCrzSale())
                        .admin(xref.getAdmin())
                        .isctype(xref.getIsctype())
                        .licempcname(xref.getLicempcname())
                        .build())
                .collect(Collectors.toList());
    }


    public static void removeSessionAttributes(String sessionId) {
        HttpSession session = getSessionById(sessionId);
        for (SessionAttribute attribute : SessionAttribute.values()) {
            session.removeAttribute(attribute.name());
        }
    }
}


