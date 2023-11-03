package com.fubon.ecplatformapi.NoUse;


import com.fubon.ecplatformapi.NoUse.captcha.CaptchaUtil;
import com.fubon.ecplatformapi.model.dto.req.FubonVerificationReqDTO;
import com.fubon.ecplatformapi.NoUse.captcha.CaptchaService;

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
    CaptchaService captchaService;
    @Autowired
    CaptchaUtil captchaUtil;


    //@GetMapping("/GetVerificationImage")
    public ResponseEntity<String> getCaptchaBase64(@RequestBody FubonVerificationReqDTO fubonVerificationReqDTO,
                                                   HttpServletRequest request, HttpServletResponse response) {

        String system = fubonVerificationReqDTO.getFBECCOMSTA1032RQ().getSystem();
        String insureType = fubonVerificationReqDTO.getFBECCOMSTA1032RQ().getInsureType();
        String verificationTypes = fubonVerificationReqDTO.getFBECCOMSTA1032RQ().getVerificationTypes();

        response.setContentType("image/png");
        String base64String = captchaUtil.generateCaptchaBase64();
        String token = "the function how to generate token";

        String jsonResponse = captchaService.generateResponseJson(system, insureType, verificationTypes,token, base64String);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(jsonResponse, headers, HttpStatus.OK);

    }

}





