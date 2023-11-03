package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {
    void setSessionAttributes(HttpSession session, FubonLoginRespDTO.UserInfo user);
    void saveSessionInfo(FubonLoginRespDTO fubonLoginRespDTO, HttpSession session);
    void removeSession(HttpSession session);
}
