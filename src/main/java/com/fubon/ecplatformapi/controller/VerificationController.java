package com.fubon.ecplatformapi.controller;


import com.fubon.ecplatformapi.model.VerificationUtil;
import com.fubon.ecplatformapi.service.VerificationService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Verification Image Base64
 * base64轉換圖片網站
 * <a href="https://www.rapidtables.com/web/tools/base64-to-image.html">...</a>
 */
@RestController
public class VerificationController {
    @Autowired
    VerificationService verificationService;

    @GetMapping("/GetVerificationImage")
    public ResponseEntity<String> getCaptchaBase64(@RequestParam String system,
                                                   @RequestParam String insureType,
                                                   @RequestParam String verificationTypes,
                                                   HttpServletRequest request, HttpServletResponse response) {

            response.setContentType("image/png");

//            VerificationUtil validateCode = new VerificationUtil();
//            String base64String = validateCode.getRandomCodeBase64(request, response);
            String base64String = verificationService.generateCaptchaBase64(request, response);
//            System.out.println("Base64 String: " + base64String);
            String jsonResponse = verificationService.generateResponseJson(system, insureType, verificationTypes, base64String);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(jsonResponse, headers, HttpStatus.OK);

    }

    @GetMapping("auth/getVerificationImage")
    public ResponseEntity<ApiRespDTO<Map<String, Object>>> getVerificationImage(@RequestParam String base64Image, @RequestParam String token) {

        try {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("verificationImage", base64Image);
            responseData.put("token", token);

            ApiRespDTO<Map<String, Object>> successResponse = ApiRespDTO.<Map<String, Object>>builder()
                    .code("0000")
                    .message("Success")
                    .data(responseData)
                    .build();

            return new ResponseEntity<>(successResponse, HttpStatus.OK);

        } catch (Exception e) {

            ApiRespDTO<Map<String, Object>> errorResponse = ApiRespDTO.<Map<String, Object>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}





