package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.enums.SessionManager;
import com.fubon.ecplatformapi.config.SessionConfig;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class SessionService {
    @Autowired
    private SessionConfig sessionConfig;
    private final String SESSION_ID = UUID.randomUUID().toString();

//    private void setSessionAttributes(MapSession mapSession, UserInfo user) {
//        SessionManager.setAttribute(mapSession, SessionManager.IDENTITY, user.getIdentify());
//        SessionManager.setAttribute(mapSession, SessionManager.EMP_NO, user.getAgent_id());
//        SessionManager.setAttribute(mapSession, SessionManager.EMP_NAME, user.getAgent_name());
//        SessionManager.setAttribute(mapSession, SessionManager.FBID, user.getId());
//        SessionManager.setAttribute(mapSession, SessionManager.UNION_NUM, user.getUnionNum());
//        SessionManager.setAttribute(mapSession, SessionManager.ADMIN_NUM, user.getAdmin_num());
//        SessionManager.setAttribute(mapSession, SessionManager.EMAIL, user.getEmail());
//        SessionManager.setAttribute(mapSession, SessionManager.XREF_INFOS, user.getXrefInfo());
//
//        List<UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
//                .map(xrefInfo -> UserInfo.XrefInfo.builder()
//                        .xref(xrefInfo.getXref())
//                        .channel(xrefInfo.getChannel())
//                        .ascCrzSale(xrefInfo.getAscCrzSale())
//                        .admin(xrefInfo.getAdmin())
//                        .build())
//                .toList();
//
//        SessionManager.setAttribute(mapSession, SessionManager.XREF_INFOS, userXrefInfoList);
//    }

    private void setSessionAttributes(HttpSession session, UserInfo user) {
        session.setAttribute(String.valueOf(SessionManager.IDENTITY), user.getIdentify());
        session.setAttribute(String.valueOf(SessionManager.EMP_NO), user.getAgent_id());
        session.setAttribute(String.valueOf(SessionManager.EMP_NAME), user.getAgent_name());
        session.setAttribute(String.valueOf(SessionManager.FBID), user.getId());
        session.setAttribute(String.valueOf(SessionManager.UNION_NUM), user.getUnionNum());
        session.setAttribute(String.valueOf(SessionManager.ADMIN_NUM), user.getAdmin_num());
        session.setAttribute(String.valueOf(SessionManager.EMAIL), user.getEmail());
        session.setAttribute(String.valueOf(SessionManager.XREF_INFOS), user.getXrefInfo());

        List<UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
                .map(xrefInfo -> UserInfo.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .channel(xrefInfo.getChannel())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .toList();

        session.setAttribute(String.valueOf(SessionManager.XREF_INFOS), userXrefInfoList);
    }


    /** 儲存登入者資訊
     *
     */
    public void saveSessionInfo(FbLoginRespDTO fbLoginRespDTO, HttpSession session){
        log.info("儲存登入者資訊#Start");
        log.info("Session ID: " + session.getId());

//        MapSessionRepository repository = sessionConfig.sessionRepository();
//        MapSession mapSession = new MapSession(sessionId);

        UserInfo user = fbLoginRespDTO.getAny().getUserInfo();
        setSessionAttributes(session, user);

//        mapSession.setMaxInactiveInterval(Duration.ofMinutes(20));
//        repository.save(mapSession);

        setSessionAttributes(session, user);
        session.setMaxInactiveInterval(1200);
    }


    /**
     * 從會話中取得先前儲存的 Session Info
     */

    public void getSessionInfo(HttpSession session) {
        log.info("取得儲存在Session中的Value#Start");
        log.info("Session ID: " + session.getId());
//        MapSessionRepository repository = sessionConfig.sessionRepository();
//        MapSession session = repository.findById(sessionId);

            for (SessionManager attribute : SessionManager.values()) {
                Object value = session.getAttribute(attribute.name());
                //SessionManager.getAttribute(session, attribute);
                log.info(attribute + ": " + value);
            }
            //printSession(session);
    }

    public void printSession(Session session) {
        for (SessionManager attribute : SessionManager.values()) {
            Object value = SessionManager.getAttribute(session, attribute);
            log.info(attribute + ": " + value);
        }
    }

    public void removeSession(HttpSession session){
        log.info("清除Session#Start");
        log.info("Session ID: " + session.getId());

//        MapSessionRepository repository = sessionConfig.sessionRepository();
//        MapSession session = repository.findById(sessionId);
        try {
            for (SessionManager attribute : SessionManager.values()) {
                //SessionManager.removeAttribute(session, attribute);
                session.removeAttribute(attribute.name());
            }
        } catch (Exception e) {
            log.error("Session 中沒有資料" + e.getMessage());
            throw e;
        }

    }

    /**
     * 將驗證碼存儲在會話中
     *
     */
    public void saveSession(String captcha) {
        log.info("將驗證碼存儲在會話中#Start");
        log.info("驗證碼: " + captcha);

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession mapSession = new MapSession(SESSION_ID);

        mapSession.setAttribute("captcha", captcha);
        mapSession.setMaxInactiveInterval(Duration.ofMinutes(20));
        repository.save(mapSession);
    }

    /**
     * 從會話中取得先前儲存的驗證碼
     *
     */

    public String getSession() {
        log.info("取得儲存在Session中的驗證碼#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession storedCode = repository.findById(SESSION_ID);

        if (storedCode != null) {
            String captcha = storedCode.getAttribute("captcha");

            return captcha;
        } else {
            return null;
        }

    }


}
