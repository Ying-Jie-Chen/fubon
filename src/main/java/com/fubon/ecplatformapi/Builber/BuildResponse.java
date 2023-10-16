package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
public class BuildResponse {

    @Autowired
    private ObjectMapper objectMapper;

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


    public FbLoginRespDTO buildLoginResponse(UserInfo userInfo) {
        log.info("建立 FubonAPI 登入的回應 #Start");

        FbLoginRespDTO response = FbLoginRespDTO.builder()
                .Header(FbLoginRespDTO.Header.builder()
                        .MsgId("7cdf926e-561a-43a7-bc52-ec947468fc66")
                        .FromSys("ECWS")
                        .ToSys("B2A")
                        .SysPwd("*****PW8SGg=")
                        .FunctionCode("FBECAPPCERT1001")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(FbLoginRespDTO.Any.builder()
                        .staffValid(true)
                        .staffValidMsg("")
                        .userInfo(userInfo)
                        .build())
                .build();
        printJSON(response);
        return response;
    }

    public FbQueryRespDTO buildListResponse() throws JsonProcessingException {
        log.info("建立 FubonAPI Query的回應 #Start");

        FbQueryRespDTO response = FbQueryRespDTO.builder()
                .policyResults(Collections.singletonList(
                        FbQueryRespDTO.PolicyResult.builder()
                                .clsGrp("險種")
                                .module("模組")
                                .polFormatid("保單號碼")
                                .rmaClinameI("被保險人姓名")
                                .rmaUidI("被保險人身份證")
                                .mohPlatno("車牌")
                                .secEffdate(Date.from(Instant.now()))
                                .secExpdate(Date.from(Instant.now()))
                                .ascIscXref("經辦代號")
                                .unPaidPrm(1000)
                                .build()
                ))
                .build();
        printJSON(response);
        //jsonStringToPojo(response);
        return response;
    }

    private String printJSON(FbQueryRespDTO response) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
        return jsonRequest;
    }

    private void jsonStringToPojo(FbQueryRespDTO response) throws JsonProcessingException {
        String expectedJson = printJSON(response);
        FbQueryRespDTO fbQueryRespDTO = objectMapper.readValue(expectedJson, FbQueryRespDTO.class);
        System.out.println(fbQueryRespDTO);
        Assertions.assertEquals(fbQueryRespDTO.getPolicyResults(), "policyResults");
    }


    public void printJSON(FbLoginRespDTO response){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
    }

    private void printJSON(VerificationResp response) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
    }


}
