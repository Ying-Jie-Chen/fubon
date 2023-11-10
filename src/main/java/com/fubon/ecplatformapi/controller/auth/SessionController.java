package com.fubon.ecplatformapi.controller.auth;

import com.fubon.ecplatformapi.helper.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public abstract class SessionController {
    public static String sessionID() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return SessionHelper.getSessionID(request);
    }
}