package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    VerificationVo getVerificationImage();

    LoginRespVo getUserInfo(LoginReqDTO loginReqDTO, HttpSession session) throws Exception;

    void saveUserToken(String authToken);

}
