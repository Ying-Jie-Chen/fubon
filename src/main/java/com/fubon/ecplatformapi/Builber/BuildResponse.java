package com.fubon.ecplatformapi.Builber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.resp.SSOTokenRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                .any(VerificationResp.any.builder()
                        .token("2da6cf70570e44658d8fd7e5c334ca03")
                        .verificationImageBase64("base64String")
                        .build())
                .build();

        printJSON(response);
        return response;
    }

    public LoginRespDTO buildLoginResponse(GetUserInfoVo getUserInfoVo) {
        log.info("建立 FubonAPI 登入的回應 #Start");

//        FubonLoginRespDTO response = FubonLoginRespDTO.builder()
//                .Header(FubonLoginRespDTO.Header.builder()
//                        .MsgId("7cdf926e-561a-43a7-bc52-ec947468fc66")
//                        .FromSys("ECWS")
//                        .ToSys("B2A")
//                        .SysPwd("*****PW8SGg=")
//                        .FunctionCode("FBECAPPCERT1001")
//                        .StatusCode("0000")
//                        .StatusDesc("成功")
//                        .build())
//                .Any(FubonLoginRespDTO.Any.builder()
//                        .staffValid(true)
//                        .staffValidMsg("")
//                        .getUserInfoVo(getUserInfoVo)
//                        .build())
//                .build();
        //printJSON(response);
        //return response;
        return null;
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

    public SSOTokenRespDTO buildSSOTokenResponse() {

        SSOTokenRespDTO response = SSOTokenRespDTO.builder()
                .Header(SSOTokenRespDTO.Header.builder()
                        .MsgId("afb1d47a-e60a-453b-ad86-f6f84de2ac5f")
                        .FromSys("ECWS")
                        .SysPwd("lYKMhPW8SGg=")
                        .FunctionCode("FBECCOMSTA1040")
                        .StatusCode("0000")
                        .StatusDesc("成功")
                        .build())
                .Any(SSOTokenRespDTO.Any.builder()
                        .sid("e795025984b84443b5b04ad343472cec")
                        .build())
                .build();
        return response;
    }

    public FubonPolicyDetailRespDTO buildPolicyDetailResponse() {
        FubonPolicyDetailRespDTO response = FubonPolicyDetailRespDTO.builder()
                // 車個險
                .ecAppInsure(FubonPolicyDetailRespDTO.EcAppInsure.builder()
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
                        .ecoEcAppWsBean(Collections.singletonList(FubonPolicyDetailRespDTO.EcoEcAppWsBeans.builder()
                                .ecoSeq(100)
                                .ecoUid("UID12345")
                                .ecoNat("TW")
                                .ecoCname("John Doe")
                                .ecoBirdate(Calendar.getInstance())
                                .ecoEmail("john@example.com")
                                .ecoMob("1234567890")
                                .ecoCompnm("Example Company")
                                .ecoContent("Sample content")
                                .ecoCrcGrp("A")
                                .ecoPtcptno("1")
                                .ecoReltn("Self")
                                .ecoAge(30)
                                .ecoPlnCode("1")
                                .build()))
                        // 要保人資料
                        .rmaAEcAppWsBean(FubonPolicyDetailRespDTO.RmaEcAppWsBean.builder()
                                .build())
                        // 信用卡資料
                        .crdEcAppWsBean(FubonPolicyDetailRespDTO.CrdEcAppWsBean.builder()
                                .build())
                        // 險種資料
                        .pitEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.PitEcAppWsBean.builder()
                                .pitSeq(100)
                                .pitBnfCode("SampleCode")
                                .pitEb0Name("SampleName")
                                .pitType("SampleType")
                                .pitFinalprm(1000.0)
                                .pitPex(500.0)
                                .pitPexUnit("")
                                .pitEb0Lists(Arrays.asList(FubonPolicyDetailRespDTO.PitEb0List.builder()
                                                .eb0TsiDesc("Desc1")
                                                .eb0TsiUnit("Unit1")
                                                .eb0TsiValue("Value1")
                                                .build(),
                                        FubonPolicyDetailRespDTO.PitEb0List.builder()
                                                .eb0TsiDesc("Desc2")
                                                .eb0TsiUnit("Unit2")
                                                .eb0TsiValue("Value2")
                                                .build()))
                                .build()))
                        // 險種名冊資料
                        .pitNEcAppWsBeans(Collections.singleton(FubonPolicyDetailRespDTO.PitNecAppWsBeans.builder()
                                .build()))
                        // 經辦資料
                        .ascEcAppWsBean(FubonPolicyDetailRespDTO.AscEcAppWsBean.builder()
                                .build())
                        // 車輛資料
                        .mohEcAppWsBean(FubonPolicyDetailRespDTO.MohEcAppWsBean.builder()
                                .mohPrmCode("002")
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
                        .midEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.MidEcAppWsBean.builder()
                                .midContent("Sample Content")
                                .midName("John Doe")
                                .midSex("Male")
                                .midSeq(1)
                                .midBirdate(Calendar.getInstance())
                                .midAge(30)
                                .build()))
                        // 航班資料
                        .fliEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.FliEcAppWsBeans.builder()
                                .build()))
                        // 受益人資料
                        .benEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.BenEcAppWsBean.builder()
                                .benPitseq(100)
                                .benRskSeq(100)
                                .benType("E")
                                .build()))
                        // 附加條款
                        .sbcEcAppWsBeans(Collections.singletonList(FubonPolicyDetailRespDTO.SbcEcAppWsBean.builder()
                                .sbcCode("附加條款代碼")
                                .sbcApply("Y")
                                .sbcMohParam1("5")
                                .sbcMohParam2(10.5)
                                .sbcMohParam3(20.3)
                                .sbcMohParam4(15.2)
                                .sbcMohParam5(8.0)
                                .build()))
                        .build())
                // 企業險
                .ecAppInsureEtp(FubonPolicyDetailRespDTO.EcAppInsureEtp.builder()
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
                                .seq(1)
                                .values(Arrays.asList("Value1", "Value2", "Value3"))
                                .build()))
                        // 保險項目標題
                        .pitColumnNames(Arrays.asList("險種代碼", "保險種類"))
                        // 保險項目
                        .pitEcAppEtpWsBeans(List.of(
                                        FubonPolicyDetailRespDTO.PitEcAppEtpWsBean.builder()
                                                .pitRskSeq(1)
                                                .pitRskType("RSK")
                                                .values(List.of("BEC11 雇主意外責任", "", ""))
                                                .build(),
                                        FubonPolicyDetailRespDTO.PitEcAppEtpWsBean.builder()
                                                .pitRskSeq(1)
                                                .pitRskType("RSK")
                                                .values(List.of("每一人體傷死亡", "TWD 8,000,000", ""))
                                                .build(),
                                        FubonPolicyDetailRespDTO.PitEcAppEtpWsBean.builder()
                                                .pitRskSeq(1)
                                                .pitRskType("RSK")
                                                .values(List.of("每一事故體傷死亡", "TWD 32,000,000", ""))
                                                .build(),
                                        FubonPolicyDetailRespDTO.PitEcAppEtpWsBean.builder()
                                                .pitRskSeq(1)
                                                .pitRskType("RSK")
                                                .values(List.of("保險期間限額", "TWD 48,000,000", ""))
                                                .build())
                        ).build())
                .build();
        printJSON(response);
        return response;
    }



    public FubonPrnDetailResp buildPrnDetailResponse() {
        FubonPrnDetailResp.PrnResult prnResult1 = FubonPrnDetailResp.PrnResult.builder()
                .build();

        FubonPrnDetailResp.PrnResult prnResult2 = FubonPrnDetailResp.PrnResult.builder()
                .build();

        List<FubonPrnDetailResp.PrnResult> prmList = Arrays.asList(prnResult1, prnResult2);

        return FubonPrnDetailResp.builder()
                .prmList(prmList)
                .build();
    }

    public FubonClmSalesRespDTO buildClmSalesResponse() {
        FubonClmSalesRespDTO.Content.ClaimInfo claimInfo1 = FubonClmSalesRespDTO.Content.ClaimInfo.builder()
                .no(1)
                .queryPlan("險種1")
                .acdate(new Date())
                .clamno("CLAIM001")
                .queryPolyNo("POLY001")
                .icname("被保險人1")
                .accper("事故人1")
                .clamsts("處理中")
                .prcdeptnm("處理單位1")
                .prcempnm("處理人員1")
                .phone("123-456-7890")
                .build();

        FubonClmSalesRespDTO.Content.ClaimInfo claimInfo2 = FubonClmSalesRespDTO.Content.ClaimInfo.builder()
                .no(2)
                .queryPlan("險種2")
                .acdate(new Date())
                .clamno("CLAIM002")
                .queryPolyNo("POLY002")
                .icname("被保險人2")
                .accper("事故人2")
                .clamsts("已結案")
                .prcdeptnm("處理單位2")
                .prcempnm("處理人員2")
                .phone("987-654-3210")
                .build();

        List<FubonClmSalesRespDTO.Content.ClaimInfo> claimInfoList = Arrays.asList(claimInfo1, claimInfo2);

        FubonClmSalesRespDTO.Content content = FubonClmSalesRespDTO.Content.builder()
                .claimInfo(claimInfoList)
                .build();

        return FubonClmSalesRespDTO.builder()
                .result(200)
                .message("成功")
                .total(2)
                .count(2)
                .content(content)
                .build();
    }

    public FubonChkEnrDataRespDTO buildChkEnrData() {
        FubonChkEnrDataRespDTO chkEnrData = FubonChkEnrDataRespDTO.builder()
                .responseCode("200")
                .responseErrorCode("0")
                .responseMsg("Success")
                .recEcAppWsBeans(new ArrayList<>())
                .build();

        FubonChkEnrDataRespDTO.RecEcAppWsBean bean1 = FubonChkEnrDataRespDTO.RecEcAppWsBean.builder()
                .formatid("1")
                .endst("Active")
                .rmaACliname("Client A")
                .rmalCliname("Client B")
                .secCdate(new Date())
                .secAdate(new Date())
                .secWrpsts("Approved")
                .closeDate(new Date())
                .build();

        chkEnrData.getRecEcAppWsBeans().add(bean1);

        return chkEnrData;
    }

    public QueryPolicyListRespDTO buildMyPolicyList() {
        return QueryPolicyListRespDTO.builder()
                .policyResults(List.of(
                        QueryPolicyListRespDTO.PolicyResult.builder()
                                .clsGrp("Class A")
                                .module("Module 1")
                                .polFormatid("PolicyFormat-001")
                                .rmaClinameI("RMA Client 1")
                                .rmaUidI("RMA_UID_001")
                                .mohPlatno("MOH123")
                                .secEffdate(Calendar.getInstance())
                                .secExpdate(Calendar.getInstance())
                                .ascIscXref("ASC_ISC_XREF_001")
                                .unPaidPrm(1000)
                                .build(),
                        QueryPolicyListRespDTO.PolicyResult.builder()
                                .clsGrp("Class B")
                                .module("Module 2")
                                .polFormatid("PolicyFormat-002")
                                .rmaClinameI("RMA Client 2")
                                .rmaUidI("RMA_UID_002")
                                .mohPlatno("MOH456")
                                .secEffdate(Calendar.getInstance())
                                .secExpdate(Calendar.getInstance())
                                .ascIscXref("ASC_ISC_XREF_002")
                                .unPaidPrm(1500)
                                .build()
                ))
                .build();
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

    public void printJSON(FubonPolicyDetailRespDTO response){
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
