package com.fubon.ecplatformapi.controller.auth;

import com.fubon.ecplatformapi.helper.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Controller
public abstract class SessionController {

    public static String sessionID() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return SessionHelper.getSessionID(request);
    }

    public static String getAuthToken() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
            return (String) session.getAttribute("AUTH_TOKEN");
        }
        return null;
    }
}