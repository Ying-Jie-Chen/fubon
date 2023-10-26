package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    VerificationImageVO getVerificationImage();

    LoginRespVo getUserInfo(LoginReq loginReq, HttpSession session) throws Exception;

    void saveUserToken(String authToken);

}
