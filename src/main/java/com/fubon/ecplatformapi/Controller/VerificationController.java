package com.fubon.ecplatformapi.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.ValidateUtil;
import com.fubon.ecplatformapi.model.dto.req.VerificationReq;
import com.fubon.ecplatformapi.model.dto.resp.VerificationRes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Verification Image Base64
 * base64轉換圖片網站
 * <a href="https://www.rapidtables.com/web/tools/base64-to-image.html">...</a>
 */
@RestController
public class VerificationController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/GetVerificationImage")
    public ResponseEntity<String> getCaptchaBase64(@RequestParam String system,
                                                   @RequestParam String insureType,
                                                   @RequestParam String verificationTypes,
                                                   HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("image/png");
            ValidateUtil validateCode = new ValidateUtil();

            String base64String = validateCode.getRandomCodeBase64(request, response);
            System.out.println("Base64 String: " + base64String);

            VerificationReq req = VerificationReq.builder()
                    .Header(VerificationReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECCOMSTA1032")
                        .build())
                    .FBECCOMSTA1032RQ(VerificationReq.FBECCOMSTA1032RQ.builder()
                            .system(system)
                            .insureType(insureType)
                            .verificationTypes(verificationTypes)
                            .build())
                    .build();

            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
            String jsonRequest = objectMapper.writeValueAsString(req);
            System.out.println(jsonRequest);

            VerificationRes resp = VerificationRes.builder()
                    .Header(VerificationRes.Header.builder()
                            .MsgId("033ef14f-345e-42a9-9114-fbfdd562909f")
                            .FromSys("ECWS")
                            .ToSys(req.getHeader().getFromSys())
                            .SysPwd(req.getHeader().getSysPwd())
                            .FunctionCode(req.getHeader().getFunctionCode())
                            .StatusCode("0000")
                            .StatusDesc("成功")
                            .build())
                    .any(VerificationRes.Any.builder()
//                            .token(getToken())
                            .verificationImageBase64(base64String)
                            .build())
                    .build();

            String jsonResponse = objectMapper.writeValueAsString(resp);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(jsonResponse, headers, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
