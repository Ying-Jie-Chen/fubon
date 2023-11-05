package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    VerificationVo getVerificationImage();

    LoginRespVo getUserInfo(LoginReqDTO loginReqDTO, HttpSession session, HttpServletResponse response) throws Exception;

    void saveVerificationImage(String outputPath, String base64String);

}
