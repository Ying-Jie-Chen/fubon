package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.GetFubonSSOTokenRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BuildResponse {

    @Autowired
    ObjectMapper objectMapper;

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

    public FbLoginRespDTO buildLoginResponse(GetUserInfoVo getUserInfoVo) {
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
                        .getUserInfoVo(getUserInfoVo)
                        .build())
                .build();
        //printJSON(response);
        return response;
    }

    public FbQueryRespDTO buildListResponse(){
        log.info("建立 FubonAPI Query的回應 #Start");
        FbQueryRespDTO response = FbQueryRespDTO.builder()
                .policyResults(List.of(
                        FbQueryRespDTO.PolicyResult.builder()
                                .clsGrp("險種1")
                                .module("模組1")
                                .polFormatid("保單號碼1")
                                .rmaClinameI("被保險人姓名1")
                                .rmaUidI("被保險人身份證1")
                                .mohPlatno("車牌1")
                                .secEffdate(new Date())
                                .secExpdate(new Date())
                                .ascIscXref("經辦代號1")
                                .unPaidPrm(1000)
                                .build(),
                        FbQueryRespDTO.PolicyResult.builder()
                                .clsGrp("險種2")
                                .module("模組2")
                                .polFormatid("保單號碼2")
                                .rmaClinameI("被保險人姓名2")
                                .rmaUidI("被保險人身份證2")
                                .mohPlatno("車牌2")
                                .secEffdate(new Date())
                                .secExpdate(new Date())
                                .ascIscXref("經辦代號2")
                                .unPaidPrm(1000)
                                .build()
                ))
                .build();
        //printJSON(response);
        return response;
    }

    public GetFubonSSOTokenRespDTO buildSSOTokenResponse() {

        GetFubonSSOTokenRespDTO response = GetFubonSSOTokenRespDTO.builder()
                .Header(GetFubonSSOTokenRespDTO.Header.builder()
                        .MsgId("afb1d47a-e60a-453b-ad86-f6f84de2ac5f")
                        .FromSys("ECWS")
                        .SysPwd("lYKMhPW8SGg=")
                        .FunctionCode("FBECCOMSTA1040")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(GetFubonSSOTokenRespDTO.Any.builder()
                        .sid("e795025984b84443b5b04ad343472cec")
                        .build())
                .build();
        return response;
    }

    public FubonPolicyDetailRespDTO buildPolicyDetailResponse() {
        FubonPolicyDetailRespDTO response = FubonPolicyDetailRespDTO.builder()
                // 車個險
                .ecAppInsure(FubonPolicyDetailRespDTO.EcAppInsure.builder()
                        .voltPolicyNum("").compPolicyNum("").voltPremium(1.1)
                        .compPremium(1.1).totalPremium(1.1).polSt("")
                        // 保單資料
                        .secEcAppWsBean(FubonPolicyDetailRespDTO.SecEcAppWsBean.builder()
                                .build())
                        // 保單其他資料
                        .eecEcAppWsBean(FubonPolicyDetailRespDTO.EecEcAppWsBean.builder()
                                .build())
                        // 被保險人資料
                        .rmalEcAppWsBean(FubonPolicyDetailRespDTO.RmaEcAppWsBean.builder()
                                .build())
                        // 被保險人名冊
                        .ecoEcAppWsBean(FubonPolicyDetailRespDTO.EcoEcAppWsBeans.builder()
                                .build())
                        // 要保人資料
                        .rmaAEcAppWsBean(FubonPolicyDetailRespDTO.RmaEcAppWsBean.builder()
                                .build())
                        // 信用卡資料
                        .crdEcAppWsBean(FubonPolicyDetailRespDTO.CrdEcAppWsBean.builder()
                                .build())
                        // 險種資料
                        .pitEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.PitEcAppWsBean.builder()
                                .build()))
                        // 險種名冊資料
                        .pitNEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.PitNecAppWsBeans.builder()
                                .build()))
                        // 經辦資料
                        .ascEcAppWsBean(FubonPolicyDetailRespDTO.AscEcAppWsBean.builder()
                                .build())
                        // 車輛資料
                        .mohEcAppWsBean(FubonPolicyDetailRespDTO.MohEcAppWsBean.builder()
                                .build())
                        // 標的物地址
                        .rskEcAppWsBean(FubonPolicyDetailRespDTO.RskEcAppWsBean.builder()
                                .build())
                        // 建築物資料
                        .budEcAppWsBean(FubonPolicyDetailRespDTO.BudEcAppWsBean.builder()
                                .build())
                        // 抵押權人資料
                        .rmaMEcAPpWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.RmaMEcAppWsBean.builder()
                                .build()))
                        // 其他標的名稱資料
                        .midEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.MidEcAPpWsBean.builder()
                                .build()))
                        // 航班資料
                        .fliEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.FliEcAppWsBeans.builder()
                                .build()))
                        // 受益人資料
                        .benEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.BenEcAppWsBean.builder()
                                .build()))
                        .build())
                // 企業險
                .ecAppInsureEtp(FubonPolicyDetailRespDTO.EcAppInsureEtp.builder()
                        .policyNum("").clsCode("").cgrCode("").plnCode("")
                        .totalPremium(1.1).totalPremium100(1.1).ourshr(1.1)
                        // 保單資料
                        .secEcAppWsBean(FubonPolicyDetailRespDTO.SecEcAppWsBean.builder()
                                .build())
                        // 被保險人資料
                        .rmaIEcAppWsBean(FubonPolicyDetailRespDTO.RmaEcAppWsBean.builder()
                                .build())
                        // 要保人資料
                        .rmaAEcAppWsBean(FubonPolicyDetailRespDTO.RmaEcAppWsBean.builder()
                                .build())
                        // 標的資料
                        .rskEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.RskEcAppEtpWsBean.builder()
                                .build()))
                        // 標的明細
                        .rskDEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean.builder()
                                .build()))
                        // 保險項目標題
                        .pitEcAppWsBean(Collections.singleton(FubonPolicyDetailRespDTO.PitEcAppEtpWsBean.builder()
                                .build()))
                        .build())
                .build();

        return response;
    }

    private void printJSON(FbQueryRespDTO response) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Json 排版
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonRequest);
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
