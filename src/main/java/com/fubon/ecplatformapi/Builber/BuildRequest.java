package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.req.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BuildRequest {

    @Autowired
    private ObjectMapper objectMapper;


    public VerificationReq buildVerificationImageRequest() {
        log.info("建立 FubonAPI 取得圖形驗證的請求 #Start");

        return  VerificationReq.builder()
                .Header(VerificationReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("lYKMhPW8SGg=")
                        .FunctionCode("FBECCOMSTA1032")
                        .account("123456")
                        .user_ip("192.168.50.138")
                        .build())
                .FBECCOMSTA1032RQ(VerificationReq.FBECCOMSTA1032.builder()
                        .system("B2A")
                        .insureType("FBINSAPP")
                        .verificationTypes("1")
                        .build())
                .build();
    }
    public FbLoginReq buildFubonLoginRequest(LoginReq loginReq) {
        log.info("建立 FubonAPI 登入的請求 #Start");

        FbLoginReq req = FbLoginReq.builder()
                .Header(FbLoginReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("lYKMhPW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .account("123456")
                        .user_ip("192.168.50.138")
                        .build())
                .FBECAPPCERT1001RQ(FbLoginReq.FunctionCode.builder()
                        .ipAddress("192.168.50.138")
                        .device("iOS")
                        .codeName("ASUS P01T_1")
                        .deviceId("589241cf66bb05f7")
                        .osVersion("6.0.1")
                        .appVersion("1.01.81")
                        .agentId("")
                        .loginId("")
                        .salesId("")
                        .agentName("")
                        .unionNum("")
                        .adminId("")
                        .returnPfx("0")
                        .identify(loginReq.getIdentify())
                        .empNo(loginReq.getAccount())
                        .password(loginReq.getPassword())
                        .verificationCode(loginReq.getVerificationCode())
                        .token(loginReq.getToken())
                        .client("")
                        .sid("")
                        .build())
                .build();

        //printJSON(req);
        return req;

    }


    public void printJSON(FbLoginReq request){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest = null;
        try {
            jsonRequest = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
    }


}
