package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.config.SessionConfig;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

// ...
@Slf4j
@Service
public class SessionService {
    @Autowired
    private SessionConfig sessionConfig;

    private static final String SESSION_KEY = "captcha";
    private String SESSION_ID = UUID.randomUUID().toString();

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

    // 儲存登入者資訊
    public void saveUserInfo(UserInfo userInfo){
        log.info("儲存登入者資訊#Start");

        MapSessionRepository repository = sessionConfig.sessionRepository();
        MapSession mapSession = new MapSession(SESSION_ID);

        mapSession.setAttribute("UserInfoKey", userInfo);
        mapSession.setMaxInactiveInterval(Duration.ofMinutes(20));
        repository.save(mapSession);

    }
}
