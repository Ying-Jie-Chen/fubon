package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface SsoService {
    String getSSOToken(String sessionId);
    void performSSOLogin(SsoReqDTO sspReq);
}
