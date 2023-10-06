package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.req.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

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
    public FbLoginReq buildFubonLoginRequest(LoginReq loginReq) {
        log.info("建立 FubonAPI 登入的請求 #Start");

        FbLoginReq req = FbLoginReq.builder()
                .Header(FbLoginReq.Header.builder()
                        .FromSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .build())
                .FBECAPPCERT1001RQ(FbLoginReq.FunctionCode.builder()
                        .returnPfx("0")
                        .identify(loginReq.getIdentify())
                        .empNo(loginReq.getAccount())
                        .password(loginReq.getPassword())
                        .verificationCode(loginReq.getVerificationCode())
                        .token(loginReq.getToken())
                        .build())
                .build();

        printJSON(req);
        return req;

    }

    public FbQueryReq buildFbQueryRequest(QueryReqDTO queryReq) {
        log.info("建立 FubonAPI Query 的請求 #Start");

        FbQueryReq req = FbQueryReq.builder()
                .clsGrp(queryReq.getInsType())
                .module("POL")
                .seeFormatid(queryReq.getPolicyNum())
                .rmaClinamel(queryReq.getInsurerName())
                .rmaUidI(queryReq.getInsurerId())
                .rmaClinameA(queryReq.getInsurerName())
                .rmaUidA(queryReq.getManagerId())
                .mohPlatno(queryReq.getPlate())
                //.secTradeNo(queryReq.transNum)
                .ascAdmin(null)
                .ascIscXref(null)
                .fbId(null)
                .dataType("secEffdate")
                .dateFr(queryReq.getEffectDateStart())
                .dateTo(queryReq.getEffectDateEnd())
                //.sourcePage()
                .build();
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
