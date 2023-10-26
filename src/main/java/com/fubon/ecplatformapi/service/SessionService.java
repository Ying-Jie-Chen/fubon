package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {
    void setSessionAttributes(HttpSession session, GetUserInfoVo user);

    void saveSessionInfo(FbLoginRespDTO fbLoginRespDTO, HttpSession session);

    void removeSession(HttpSession session);
}
