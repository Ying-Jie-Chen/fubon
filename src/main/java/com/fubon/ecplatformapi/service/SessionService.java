package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.enums.SessionManager;
import com.fubon.ecplatformapi.model.config.SessionConfig;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

// ...
@Slf4j
@Service
public class SessionService {
    @Autowired
    private SessionConfig sessionConfig;

    private final String SESSION_ID = UUID.randomUUID().toString();

    /** 儲存登入者資訊
     *
     * @param fubonLoginResp
     */
    public void saveSessionInfo(FubonLoginResp fubonLoginResp){
        log.info("儲存登入者資訊#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession mapSession = new MapSession(SESSION_ID);

        UserInfo user = fubonLoginResp.getAny().getUserInfo();
        SessionManager.setAttribute(mapSession, SessionManager.IDENTITY, user.getIdentify());
        SessionManager.setAttribute(mapSession, SessionManager.EMP_NO, user.getAgent_id());
        SessionManager.setAttribute(mapSession, SessionManager.EMP_NAME, user.getAgent_name());
        SessionManager.setAttribute(mapSession, SessionManager.FBID, user.getId());
        SessionManager.setAttribute(mapSession, SessionManager.XREF_INFOS, user.getXrefInfo());

        List<UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
                .map(xrefInfo -> UserInfo.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .channel(xrefInfo.getChannel())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .toList();

        SessionManager.setAttribute(mapSession, SessionManager.XREF_INFOS, userXrefInfoList);

        mapSession.setMaxInactiveInterval(Duration.ofMinutes(20));
        repository.save(mapSession);

    }

    /**
     * 從會話中取得先前儲存的 Session Info
     *
     * @return
     */

    public void getSessionInfo() {
        log.info("取得儲存在Session中的Value#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession storedInfo = repository.findById(SESSION_ID);

        if (storedInfo != null) {
            SessionManager.getAttribute(storedInfo, SessionManager.IDENTITY);
            SessionManager.getAttribute(storedInfo, SessionManager.EMP_NO);
            SessionManager.getAttribute(storedInfo, SessionManager.EMP_NAME);
            SessionManager.getAttribute(storedInfo, SessionManager.FBID);
            List<UserInfo.XrefInfo> xrefInfos = SessionManager.getXrefInfoAttribute(storedInfo);
        }
        printSession(storedInfo);
    }

    public void printSession(Session session) {
        for (SessionManager attribute : SessionManager.values()) {
            Object value = SessionManager.getAttribute(session, attribute);
            System.out.println("Attribute Name: " + attribute + ", Value: " + value);
        }
    }

    public void removeSession(){
        log.info("清除Session#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession session = repository.findById(SESSION_ID);
        try {
            SessionManager.removeAttribute(session, SessionManager.IDENTITY);
            SessionManager.removeAttribute(session, SessionManager.EMP_NO);
            SessionManager.removeAttribute(session, SessionManager.EMP_NAME);
            SessionManager.removeAttribute(session, SessionManager.FBID);
            SessionManager.removeAttribute(session, SessionManager.XREF_INFOS);

            printSession(session);

        } catch (Exception e) {
            log.error("Session 中沒有資料" + e.getMessage());
            throw e;
        }

    }


    /**
     * 將驗證碼存儲在會話中
     *
     * @param captcha
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
     * @return
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
