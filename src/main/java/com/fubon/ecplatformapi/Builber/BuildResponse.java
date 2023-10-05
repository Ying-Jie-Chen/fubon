package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class BuildResponse {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionService sessionService;

    public VerificationResp buildVerificationImageResponse() {
        log.info("建立 FubonAPI 取得圖形驗證的回應 #Start");

        VerificationResp response = VerificationResp.builder()
                .Header(VerificationResp.Header.builder()
                        .MsgId("033ef14f-345e-42a9-9114-fbfdd562909")
                        .FromSys("ECWS")
                        .ToSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECCOMSTA1032")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(VerificationResp.Any.builder()
                        .token("2da6cf70570e44658d8fd7e5c334ca03")
                        .verificationImageBase64("base64String")
                        .build())
                .build();

        printJSON(response);
        return response;
    }


    public FubonLoginResp buildLoginResponse(UserInfo userInfo) {
        log.info("建立 FubonAPI 登入的回應 #Start");

        FubonLoginResp response = FubonLoginResp.builder()
                .Header(FubonLoginResp.Header.builder()
                        .MsgId("7cdf926e-561a-43a7-bc52-ec947468fc66")
                        .FromSys("ECWS")
                        .ToSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(FubonLoginResp.Any.builder()
                        .staffValid(true)
                        .staffValidMsg("")
                        .userInfo(userInfo)
                        .build())
                .build();
        printJSON(response);
        return response;
    }


    public void printJSON(FubonLoginResp response){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
    }

    private void printJSON(VerificationResp response) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
    }

}
