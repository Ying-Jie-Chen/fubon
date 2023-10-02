package com.fubon.ecplatformapi.controller;


import com.fubon.ecplatformapi.FubonLoginReq;
import com.fubon.ecplatformapi.model.dto.req.VerificationReq;
import com.fubon.ecplatformapi.model.dto.resp.VerificationRes;
import com.fubon.ecplatformapi.service.VerificationService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


/**
 * Verification Image Base64
 * base64轉換圖片網站
 * <a href="https://www.rapidtables.com/web/tools/base64-to-image.html">...</a>
 */
@Slf4j
@RestController
public class VerificationController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    VerificationService verificationService;

    @GetMapping("/GetVerificationImage")
    public ResponseEntity<String> getCaptchaBase64(@RequestBody VerificationReq verificationReq,
                                                   HttpServletRequest request, HttpServletResponse response) {

        String system = verificationReq.getFBECCOMSTA1032RQ().getSystem();
        String insureType = verificationReq.getFBECCOMSTA1032RQ().getInsureType();
        String verificationTypes = verificationReq.getFBECCOMSTA1032RQ().getVerificationTypes();

        response.setContentType("image/png");
        String base64String = verificationService.generateCaptchaBase64(request);
        String token = "the function how to generate token";
        log.info("base64 String: " + base64String);
        String jsonResponse = verificationService.generateResponseJson(system, insureType, verificationTypes,token, base64String);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(jsonResponse, headers, HttpStatus.OK);

    }

    @PostMapping("/Login")
    public ResponseEntity<String> getCaptchaBase64(@RequestBody FubonLoginReq fubonLoginReq) {
    }

}





