package com.fubon.ecplatformapi.controller;


import com.fubon.ecplatformapi.FubonLoginResp;
import com.fubon.ecplatformapi.LoginService;
import com.fubon.ecplatformapi.captcha.CaptchaUtil;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.req.VerificationReq;
import com.fubon.ecplatformapi.captcha.VerificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


/**
 * Verification Image Base64
 * base64轉換圖片網站
 * <a href="https://www.rapidtables.com/web/tools/base64-to-image.html">...</a>
 */
@Slf4j
@RestController
public class VerificationController {
    @Autowired
    VerificationService verificationService;
    @Autowired
    CaptchaUtil captchaUtil;
    @Autowired
    LoginService loginService;

    @GetMapping("/GetVerificationImage")
    public ResponseEntity<String> getCaptchaBase64(@RequestBody VerificationReq verificationReq,
                                                   HttpServletRequest request, HttpServletResponse response) {

        String system = verificationReq.getFBECCOMSTA1032RQ().getSystem();
        String insureType = verificationReq.getFBECCOMSTA1032RQ().getInsureType();
        String verificationTypes = verificationReq.getFBECCOMSTA1032RQ().getVerificationTypes();

        response.setContentType("image/png");
        String base64String = captchaUtil.generateCaptchaBase64();
        String token = "the function how to generate token";

        String jsonResponse = verificationService.generateResponseJson(system, insureType, verificationTypes,token, base64String);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(jsonResponse, headers, HttpStatus.OK);

    }

    @PostMapping("/Login")
    public FubonLoginResp getCaptchaBase64(@RequestBody LoginReq loginReq) {
        return  loginService.login(loginReq);
    }

}





