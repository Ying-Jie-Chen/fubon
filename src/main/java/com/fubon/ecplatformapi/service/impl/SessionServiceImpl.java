package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.config.SessionConfig;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.SessionService;
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
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionConfig sessionConfig;
    private final String SESSION_ID = UUID.randomUUID().toString();

    @Override
    public void setSessionAttributes(HttpSession session, UserInfo user) {
        session.setAttribute(String.valueOf(SessionAttribute.IDENTITY), user.getIdentify());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NO), user.getAgent_id());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NAME), user.getAgent_name());
        session.setAttribute(String.valueOf(SessionAttribute.FBID), user.getId());
        session.setAttribute(String.valueOf(SessionAttribute.UNION_NUM), user.getUnionNum());
        session.setAttribute(String.valueOf(SessionAttribute.ADMIN_NUM), user.getAdmin_num());
        session.setAttribute(String.valueOf(SessionAttribute.EMAIL), user.getEmail());
        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), user.getXrefInfo());

        List<UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
                .map(xrefInfo -> UserInfo.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .channel(xrefInfo.getChannel())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .toList();

        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), userXrefInfoList);
    }


    /**
     * 儲存登入者資訊
     *
     */
    @Override
    public void saveSessionInfo(FbLoginRespDTO fbLoginRespDTO, HttpSession session){
        log.info("儲存登入者資訊#Start");
        UserInfo user = fbLoginRespDTO.getAny().getUserInfo();
        setSessionAttributes(session, user);
        session.setMaxInactiveInterval(1200);
    }


    public void removeSession(HttpSession session){
        log.info("清除Session#Start");

        try {
            for (SessionAttribute attribute : SessionAttribute.values()) {
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
            printSession();
            return storedCode.getAttribute("captcha");
        } else {
            return null;
        }
    }
    public void printSession() {
//        for (SessionAttribute attribute : SessionAttribute.values()) {
//            Object value = SessionAttribute.getAttribute(session, attribute);
//            log.info(attribute + ": " + value);
//        }
    }


}
