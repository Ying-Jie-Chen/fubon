package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.config.SessionConfig;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.entity.SessionInfo;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// ...
@Slf4j
@Service
public class SessionService {
    @Autowired
    private SessionConfig sessionConfig;

    private static final String SESSION_KEY = "captcha";
    private String SESSION_ID = UUID.randomUUID().toString();

    /** 儲存登入者資訊
     *
     * @param sessionInfo
     */
    public void saveSessionInfo(SessionInfo sessionInfo){
        log.info("儲存登入者資訊#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession mapSession = new MapSession(SESSION_ID);

        mapSession.setAttribute("SessionManager", sessionInfo);
        mapSession.setMaxInactiveInterval(Duration.ofMinutes(20));
        repository.save(mapSession);

    }

    /**
     * 從會話中取得先前儲存的 Session Info
     *
     * @return
     */

    public SessionInfo getSessionInfo() {
        log.info("取得儲存在Session中的SessionInfo#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession storedInfo = repository.findById(SESSION_ID);

        if (storedInfo != null) {
            SessionInfo info = storedInfo.getAttribute("SessionManager");
            return info;
        } else {
            return null;
        }

    }

    public SessionInfo createSessionInfo(FubonLoginResp fubonLoginResp) {
        UserInfo user = fubonLoginResp.getAny().getUserInfo();
        List<UserInfo.XrefInfo> xrefInfoList = user.getXrefInfo();

        List<SessionInfo.XrefInfo> sessionXrefInfoList = xrefInfoList.stream()
                .map(xrefInfo -> SessionInfo.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .channel(xrefInfo.getChannel())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .collect(Collectors.toList());

        return SessionInfo.builder()
                .identify(user.getIdentify())
                .empNo(user.getAgent_id())
                .empName(user.getAgent_name())
                .fbid(user.getId())
                .xrefInfos(sessionXrefInfoList)
                .build();
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

        mapSession.setAttribute(SESSION_KEY, captcha);
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
            String captcha = storedCode.getAttribute(SESSION_KEY);

            return captcha;
        } else {
            return null;
        }

    }


}
