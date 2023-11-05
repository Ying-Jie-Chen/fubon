package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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


    public static void saveSession(HttpSession session, HttpServletResponse response, FubonLoginRespDTO fbLoginRespDTO){
        associateSession(session, session.getId());
        setSessionAttributes(session.getId(), fbLoginRespDTO.getAny().getUserInfo());
        //SessionHelper.getAllValue(sessionId);
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

    public static void setSessionAttributes(String sessionId, FubonLoginRespDTO.UserInfo user) {
        HttpSession session = getSessionById(sessionId);
        session.setMaxInactiveInterval(1200);

        session.setAttribute(String.valueOf(SessionAttribute.IDENTITY), user.getIdentity());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NO), user.getAgent_id());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NAME), user.getAgent_name());
        session.setAttribute(String.valueOf(SessionAttribute.FBID), user.getId());
        session.setAttribute(String.valueOf(SessionAttribute.UNION_NUM), user.getUnion_num());
        session.setAttribute(String.valueOf(SessionAttribute.ADMIN_NUM), user.getAdmin_num());
        session.setAttribute(String.valueOf(SessionAttribute.EMAIL), user.getEmail());
        session.setAttribute(String.valueOf(SessionAttribute.SALES_ID), user.getSales_id());
        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), user.getXrefInfo());

//        List<FubonLoginRespDTO.UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
//                .map(xrefInfo -> FubonLoginRespDTO.UserInfo.XrefInfo.builder()
//                        .xref(xrefInfo.getXref())
//                        .channel(xrefInfo.getChannel())
//                        .regno(xrefInfo.getRegno())
//                        .empcname(xrefInfo.getEmpcname())
//                        .adminSeq(xrefInfo.getAdminSeq())
//                        .ascCrzSale(xrefInfo.getAscCrzSale())
//                        .admin(xrefInfo.getAdmin())
//                        .isctype(xrefInfo.getIsctype())
//                        .licempcname(xrefInfo.getLicempcname())
//                        .build())
//                .collect(Collectors.toList());
//
//        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), userXrefInfoList);
    }

    public static void removeSessionAttributes(String sessionId) {
        HttpSession session = getSessionById(sessionId);
        for (SessionAttribute attribute : SessionAttribute.values()) {
            session.removeAttribute(attribute.name());
        }
    }
}


