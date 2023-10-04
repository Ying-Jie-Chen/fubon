package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReq;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.req.VerificationReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class BuildRequest {

    @Autowired
    private ObjectMapper objectMapper;

    public VerificationReq buildVerificationImageRequest() {
        log.info("建立 FubonAPI FBECCOMSTA1032 的請求 #Start");

        return  VerificationReq.builder()
                .Header(VerificationReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECCOMSTA1032")
                        .build())
                .FBECCOMSTA1032RQ(VerificationReq.FBECCOMSTA1032.builder()
                        .system("B2A")
                        .insureType("FBINSAPP")
                        .verificationTypes("7")
                        .build())
                .build();
    }
    public FubonLoginReq buildFubonLoginRequest(LoginReq loginRequest) {
        log.info("建立 FubonAPI FBECAPPCERT1001 的請求 #Start");

        FubonLoginReq req = FubonLoginReq.builder()
                .Header(FubonLoginReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .build())
                .FBECAPPCERT1001RQ(FubonLoginReq.FunctionCode.builder()
                        .returnPfx("0")
                        .identify(loginRequest.getIdentify())
                        .empNo(loginRequest.getAccount())
                        .password(loginRequest.getPassword())
                        .verificationCode(loginRequest.getVerificationCode())
                        .token(loginRequest.getToken())
                        .build())
                .build();

        printJSON(req);
        return req;

    }


    public void printJSON(FubonLoginReq request){
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
