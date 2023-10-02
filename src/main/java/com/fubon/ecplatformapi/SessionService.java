package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

// ...

@Service
public class SessionService {
    @Autowired
    private MapSessionRepository sessionRepository;

    public void storeUserInfoInSession(String sessionId, UserInfo userInfo) {
        Session session = sessionRepository.findById(sessionId);
        if (session != null) {
            session.setAttribute("userInfo", userInfo);
            //sessionRepository.save(session);
        }
    }
}
