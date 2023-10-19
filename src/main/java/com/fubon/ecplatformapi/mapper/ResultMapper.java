package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;

import java.util.Arrays;
import java.util.Collections;


public class ResultMapper {

    public PolicyListResultVO mapToResultVO(FbQueryRespDTO.PolicyResult policyResult) {
        return new PolicyListResultVO(
                policyResult.getClsGrp(),
                policyResult.getPolFormatid(),
                policyResult.getUnPaidPrm(),
                policyResult.getRmaClinameI(),
                policyResult.getMohPlatno(),
                policyResult.getSecEffdate(),
                policyResult.getSecExpdate()
        );
    }

    public static DetailResultVo mapToDetailResult(FubonPolicyDetailRespDTO resp) {

        FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure = resp.getEcAppInsure();
        FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp = resp.getEcAppInsureEtp();


        DetailResultVo.InsuranceInfo insuranceInfo = DetailResultVo.InsuranceInfo.builder()
                //保單基本資料
                .basicInfo(DetailResultVo.BasicInfo.builder()
                        .policyNum(ecAppInsure.getSecEcAppWsBean().getSecFormatid())
                        .effectDateStart(ecAppInsure.getSecEcAppWsBean().getSecEffdate())
                        .effectDateEnd(ecAppInsure.getSecEcAppWsBean().getSecExpdate())
                        .aEffectDateStart(ecAppInsure.getSecEcAppWsBean().getSecAeffdate())
                        .effectDate(ecAppInsure.getSecEcAppWsBean().getSecEffdate())
                        .premium(ecAppInsure.getTotalPremium())
                        .policyStatus(ecAppInsure.getPolSt())
                        .voltPremium(ecAppInsure.getVoltPremium())
                        .compPremium(ecAppInsure.getCompPremium())
                        .isEIP(ecAppInsure.getSecEcAppWsBean().getSecEip())
                        .fubonLifeAgree(ecAppInsure.getSecEcAppWsBean().getSecFubonlifeAgree())
                        .cgrCode(ecAppInsureEtp.getCgrCode())
                        .clsCode(ecAppInsureEtp.getClsCode())
                        .pnCode(ecAppInsureEtp.getPlnCode())
                        .totalPremium100(ecAppInsureEtp.getTotalPremium100())
                        .ourshr(ecAppInsureEtp.getOurshr())
                        .ptcptNo(ecAppInsure.getEcoEcAppWsBean().getEcoPtcptno())
                        .build())
                // 被保險人資訊
                .insuredInfo(DetailResultVo.InsuredInfo.builder()
                        .insuredName(ecAppInsure.getRmalEcAppWsBean().getRmaCliname())
                        .insuredId(ecAppInsure.getRmalEcAppWsBean().getRmaUid())
                        .insuredBirthDate(ecAppInsure.getRmalEcAppWsBean().getRmaPBirth())
                        .insuredAddr(ecAppInsure.getRmalEcAppWsBean().getRmaAddr())
                        .insuredTel1(ecAppInsure.getRmalEcAppWsBean().getRmaTel1())
                        .insuredTel2(ecAppInsure.getRmalEcAppWsBean().getRmaTel2())
                        .insuredEmail(ecAppInsure.getRmalEcAppWsBean().getRmaEmail())
                        .insuredPhone(ecAppInsure.getRmalEcAppWsBean().getRmaMobTel())
                        .insuredCompany(null)
                        .insuredContent(null)
                        .insuredCrcGrop(null)
                        .insuredPlanCode(null)
                        .insuredQualification(null)
                        .insuredRepresentative(ecAppInsure.getRmalEcAppWsBean().getRmaRepresentative())
                        .insuredRepresentativeId(ecAppInsure.getRmalEcAppWsBean().getRmaRepresentativeId())
                        .build())
                // 被保險人名冊
                .insuredList(Collections.singletonList(
                        DetailResultVo.InsuredList.builder()
                                .insuredName(ecAppInsure.getEcoEcAppWsBean().getEcoCname())
                                .insuredId(ecAppInsure.getEcoEcAppWsBean().getEcoUid())
                                .insuredBirthDate(ecAppInsure.getEcoEcAppWsBean().getEcoBirdate())
                                .insuredAddr(ecAppInsure.getRmalEcAppWsBean().getRmaAddr())
                                .insuredTel1(ecAppInsure.getRmalEcAppWsBean().getRmaTel1())
                                .insuredTel2(ecAppInsure.getRmalEcAppWsBean().getRmaTel2())
                                .insuredEmail(ecAppInsure.getEcoEcAppWsBean().getEcoEmail())
                                .insuredPhone(ecAppInsure.getEcoEcAppWsBean().getEcoMob())
                                .insuredCompany(ecAppInsure.getEcoEcAppWsBean().getEcoCompnm())
                                .insuredContent(ecAppInsure.getEcoEcAppWsBean().getEcoContent())
                                .insuredCrcGrop(ecAppInsure.getEcoEcAppWsBean().getEcoCrcGrp())
                                .insuredPlanCode("待確認")
                                .insuredQualification(ecAppInsure.getRmalEcAppWsBean().getRmaQualification())
                                .insuredRepresentative(ecAppInsure.getRmalEcAppWsBean().getRmaRepresentative())
                                .insuredRepresentativeId(ecAppInsure.getRmalEcAppWsBean().getRmaRepresentativeId())
                                .title(ecAppInsure.getEcoEcAppWsBean().getEcoReltn())
                                .beneficiary("太麻煩先放著")
                                .build()))
                // 要保人資訊
                .proposerInfo(DetailResultVo.ProposerInfo.builder()
                        .proposerName(ecAppInsure.getRmaAEcAppWsBean().getRmaCliname())
                        .proposerId(ecAppInsure.getRmaAEcAppWsBean().getRmaUid())
                        .proposerBirthDate(ecAppInsure.getRmaAEcAppWsBean().getRmaPBirth())
                        .proposerAddr(ecAppInsure.getRmaAEcAppWsBean().getRmaAddr())
                        .proposerTel1(ecAppInsure.getRmaAEcAppWsBean().getRmaTel1())
                        .proposerTel2(ecAppInsure.getRmaAEcAppWsBean().getRmaTel2())
                        .proposerEmail(ecAppInsure.getRmaAEcAppWsBean().getRmaEmail())
                        .totalNum(null)
                        .relation(ecAppInsure.getRmaAEcAppWsBean().getRmaRela())
                        //.payer(ecAppInsure.getSecEcAppWsBean().getSecMtg)
                        .payMode(ecAppInsure.getSecEcAppWsBean().getSecPayMode())
                        .payMethod(ecAppInsure.getSecEcAppWsBean().getSecPaymthd())
                        .cardNo(ecAppInsure.getCrdEcAppWsBean().getCrdCardno())
                        .cardExpDate(ecAppInsure.getCrdEcAppWsBean().getCrdExpdate())
                        .proposerRepresentative(ecAppInsure.getRmaAEcAppWsBean().getRmaRepresentative())
                        .proposerRepresentativeId(ecAppInsure.getRmaAEcAppWsBean().getRmaRepresentativeId())
                        .build())
                // 航班資訊
                .flightInfo(Collections.singletonList(
                DetailResultVo.FlightInfo.builder()
//                        .filghtSeq(ecAppInsure.getFliEcAppWsBeans().getFliRskSeq())
//                        .filghtCode(ecAppInsure.getFliEcAppWsBeans().getFliFlightCode())
//                        .filghtType(ecAppInsure.getFliEcAppWsBeans().getFliFlightType())
//                        .filghtNo(ecAppInsure.getFliEcAppWsBeans().getFliFlightNo())
//                        .filghtDate(ecAppInsure.getFliEcAppWsBeans().getFliFlightDate())
//                        .filghtCity(ecAppInsure.getFliEcAppWsBeans().getFliFlightCty())
                        .build()))

                .build();


        DetailResultVo resultVo = DetailResultVo.builder()
                .insuranceInfo(insuranceInfo)
                .build();

        return resultVo;
    }
}
