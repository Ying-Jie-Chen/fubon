package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class ResultMapper {

    public static PolicyListResultVO mapToResultVO(FbQueryRespDTO.PolicyResult policyResult) {
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

        // 企業險保險標的
        DetailResultVo.EtpInsuranceSubject etpInsuranceSubject = getEtpInsuranceSubject(ecAppInsureEtp);
        // 企業險保險標的明細
        DetailResultVo.EtpInsuranceSubjectDetail etpInsuranceSubjectDetail = getEtpInsuranceSubjectDetail(ecAppInsureEtp);
        // 保險項目
        DetailResultVo.InsuranceItem insuranceItem = getInsuranceItem(ecAppInsure, ecAppInsureEtp);
        // 險種名冊
        DetailResultVo.InsuranceList insuranceList = getInsuranceList();
        // 其他險種名冊
        DetailResultVo.InsuranceOtherList insuranceOtherList = getInsuranceOtherList();
        // 保單寄送記錄
        DetailResultVo.PolicyDeliveryRecord policyDeliveryRecord = getPolicyDeliveryRecord();
        // 未繳保費
        DetailResultVo.UnpaidRecord unpaidRecord = getUnpaidRecord();
        // 繳費紀錄
        DetailResultVo.PaidRecord paidRecord = getPaidRecord();
        // 理賠紀錄
        DetailResultVo.ClaimRecord claimRecord = getClaimRecord();
        // 保全紀錄
        DetailResultVo.ConservationRecord conservationRecord = getConservationRecord();

        return DetailResultVo.builder()
                //保單基本資料
                .insuranceInfo(getInsuranceInfo(ecAppInsure, ecAppInsureEtp))
                // 保險標的
                .insuranceSubject(getInsuranceSubject(ecAppInsure))
                .etpInsuranceSubject(Collections.singletonList(etpInsuranceSubject))
                .etpInsuranceSubjectDetail(Collections.singletonList(etpInsuranceSubjectDetail))
                .insuranceItem(Collections.singletonList(insuranceItem))
                .insuranceList(Collections.singletonList(insuranceList))
                .insuranceOtherList(Collections.singletonList(insuranceOtherList))
                .policyDeliveryRecord(Collections.singletonList(policyDeliveryRecord))
                .unpaidRecord(Collections.singletonList(unpaidRecord))
                .paidRecord(Collections.singletonList(paidRecord))
                .claimRecord(Collections.singletonList(claimRecord))
                .conservationRecord(Collections.singletonList(conservationRecord))
                .build();
    }

    private static DetailResultVo.InsuranceInfo getInsuranceInfo(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsure.getSecEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmal = ecAppInsure.getRmalEcAppWsBean();
        FubonPolicyDetailRespDTO.EcoEcAppWsBeans eco = ecAppInsure.getEcoEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmaA = ecAppInsure.getRmaAEcAppWsBean();
        FubonPolicyDetailRespDTO.CrdEcAppWsBean crd = ecAppInsure.getCrdEcAppWsBean();

        return DetailResultVo.InsuranceInfo.builder()
                .basicInfo(DetailResultVo.BasicInfo.builder()
                        .policyNum(sec.getSecFormatid())
                        .effectDateStart(sec.getSecEffdate())
                        .effectDateEnd(sec.getSecExpdate())
                        .aEffectDateStart(sec.getSecAeffdate())
                        .effectDate(sec.getSecEffdate())
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
                        .insuredName(rmal.getRmaCliname())
                        .insuredId(rmal.getRmaUid())
                        .insuredBirthDate(rmal.getRmaPBirth())
                        .insuredAddr(rmal.getRmaAddr())
                        .insuredTel1(rmal.getRmaTel1())
                        .insuredTel2(rmal.getRmaTel2())
                        .insuredEmail(rmal.getRmaEmail())
                        .insuredPhone(rmal.getRmaMobTel())
                        .insuredCompany(null)
                        .insuredContent(null)
                        .insuredCrcGrop(null)
                        .insuredPlanCode(null)
                        .insuredQualification(null)
                        .insuredRepresentative(rmal.getRmaRepresentative())
                        .insuredRepresentativeId(rmal.getRmaRepresentativeId())
                        .build())
                // 被保險人名冊
                .insuredList(Collections.singletonList(
                        DetailResultVo.InsuredList.builder()
                                .insuredName(eco.getEcoCname())
                                .insuredId(eco.getEcoUid())
                                .insuredBirthDate(eco.getEcoBirdate())
                                .insuredAddr(rmal.getRmaAddr())
                                .insuredTel1(rmal.getRmaTel1())
                                .insuredTel2(rmal.getRmaTel2())
                                .insuredEmail(eco.getEcoEmail())
                                .insuredPhone(eco.getEcoMob())
                                .insuredCompany(eco.getEcoCompnm())
                                .insuredContent(eco.getEcoContent())
                                .insuredCrcGrop(eco.getEcoCrcGrp())
                                .insuredPlanCode("待確認")
                                .insuredQualification(rmal.getRmaQualification())
                                .insuredRepresentative(rmal.getRmaRepresentative())
                                .insuredRepresentativeId(rmal.getRmaRepresentativeId())
                                .title(eco.getEcoReltn())
                                .beneficiary("太麻煩先放著")
                                .age(eco.getEcoAge())
                                .build()))
                // 要保人資訊
                .proposerInfo(DetailResultVo.ProposerInfo.builder()
                        .proposerName(rmaA.getRmaCliname())
                        .proposerId(rmaA.getRmaUid())
                        .proposerBirthDate(rmaA.getRmaPBirth())
                        .proposerAddr(rmaA.getRmaAddr())
                        .proposerTel1(rmaA.getRmaTel1())
                        .proposerTel2(rmaA.getRmaTel2())
                        .proposerEmail(rmaA.getRmaEmail())
                        .totalNum(null)
                        .relation(rmaA.getRmaRela())
                        .payer(sec.getSetMtg())
                        .payMode(sec.getSecPayMode())
                        .payMethod(sec.getSecPaymthd())
                        .cardNo(crd.getCrdCardno())
                        .cardExpDate(crd.getCrdExpdate())
                        .proposerRepresentative(rmaA.getRmaRepresentative())
                        .proposerRepresentativeId(rmaA.getRmaRepresentativeId())
                        .build())
                // 航班資訊
                .flightInfo(ecAppInsure.getFliEcAppWsBeans().stream()
                        .map(fliEcAppWsBean -> DetailResultVo.FlightInfo.builder()
                                .flightSeq(fliEcAppWsBean.getFliRskSeq())
                                .flightCode(fliEcAppWsBean.getFliFlightType())
                                .flightNo(fliEcAppWsBean.getFliFlightCode() + fliEcAppWsBean.getFliFlightNo())
                                .flightDate(fliEcAppWsBean.getFliFlightDate())
                                .flightCity(fliEcAppWsBean.getFliFlightCty())
                                .build())
                        .collect(toList()))
                .build();
    }

    private static DetailResultVo.InsuranceSubject getInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        FubonPolicyDetailRespDTO.MohEcAppWsBean moh = ecAppInsure.getMohEcAppWsBean();
        FubonPolicyDetailRespDTO.RskEcAppWsBean rsk = ecAppInsure.getRskEcAppWsBean();
        FubonPolicyDetailRespDTO.BudEcAppWsBean bud = ecAppInsure.getBudEcAppWsBean();
        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsure.getSecEcAppWsBean();
        FubonPolicyDetailRespDTO.EecEcAppWsBean eec = ecAppInsure.getEecEcAppWsBean();

        return  DetailResultVo.InsuranceSubject.builder()
                .plateNo(moh.getMohPlatno())
                .plateCode(moh.getMohMotCode())
                .lisnDate(moh.getMohLisndate())
                .leaveFactoryYearDate(moh.getMohBuidyr())
                .displa(moh.getMohDispla())
                .peopno1(moh.getMohPeopno1())
                .engineNo(moh.getMohEngnno())
                .vehTramak(moh.getMohVehTramak())
                .subjectAddr(rsk.getRskLocation())
                .buildYear(bud.getBudBuildyear())
                .buildOccp(bud.getBudOccp())
                .buildRoof(bud.getBudRoof())
                .buildMaterial(bud.getBudMaterial())
                .buildConstr(bud.getBudConstr())
                .buildSection(rsk.getRskSection())
                .buildHeight(bud.getBudHeight())
                .buildSpace(Double.valueOf(bud.getBudSpace() + bud.getBudSpaceUnit()))
                .mortgagee(ecAppInsure.getRmaMEcAPpWsBeans().stream()
                        .map(FubonPolicyDetailRespDTO.RmaMEcAppWsBean::getRmaCliname)
                        .collect(toList()))
                .buildFloor(bud.getBudFlrType())
                .buildAddFeeFlag(bud.getBudAddfeeMark())
                .totalNum(sec.getSecTotalnum())
                .travelCity1(sec.getSecCty1())
                .travelCity2(sec.getSecCty2())
                .travelCity3(sec.getSecCty3())
                .travelCity4(sec.getSecCty4())
                .mountainLocation(eec.getEecMountain())
                .activityLoaction(eec.getEecActivity())
                .build();
    }

    private static DetailResultVo.EtpInsuranceSubject getEtpInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskEcAppEtpWsBean> rskEc = ecAppInsureEtp.getRskEcAppWsBeans();

        return DetailResultVo.EtpInsuranceSubject.builder()
                .content(rskEc.stream()
                        .map(FubonPolicyDetailRespDTO.RskEcAppEtpWsBean::getContent)
                        .toList().toString())
                .desc(rskEc.stream()
                        .map(FubonPolicyDetailRespDTO.RskEcAppEtpWsBean::getDesc)
                        .toList().toString())
                .build();
    }

    private static DetailResultVo.EtpInsuranceSubjectDetail getEtpInsuranceSubjectDetail(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean> rskDE = ecAppInsureEtp.getRskDEcAppWsBeans();

        return DetailResultVo.EtpInsuranceSubjectDetail.builder()
                .seq(Integer.valueOf(rskDE.stream()
                        .map(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean::getSeq)
                        .toList().toString()))
                .values(Collections.singletonList(rskDE.stream()
                        .map(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean::getValues)
                        .toList().toString()))
                .build();
    }

    private static DetailResultVo.InsuranceItem getInsuranceItem(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        return DetailResultVo.InsuranceItem.builder()
                .bnfCode()
                .eb0Lists()
                .finalPrm()
                .pex()
                .seq()
                .values()
                .build();
    }

    private static DetailResultVo.InsuranceList getInsuranceList() {
        return DetailResultVo.InsuranceList.builder()
                .build();
    }

    private static DetailResultVo.InsuranceOtherList getInsuranceOtherList() {
        return DetailResultVo.InsuranceOtherList.builder()
                .build();
    }

    private static DetailResultVo.PolicyDeliveryRecord getPolicyDeliveryRecord() {
        return  DetailResultVo.PolicyDeliveryRecord.builder()
                .build();
    }

    private static DetailResultVo.UnpaidRecord getUnpaidRecord() {
        return   DetailResultVo.UnpaidRecord.builder()
                .build();
    }

    private static DetailResultVo.PaidRecord getPaidRecord() {
        return   DetailResultVo.PaidRecord.builder()
                .build();
    }

    private static DetailResultVo.ClaimRecord getClaimRecord() {
        return  DetailResultVo.ClaimRecord.builder()
                .build();
    }

    private static DetailResultVo.ConservationRecord getConservationRecord() {
        return DetailResultVo.ConservationRecord.builder()
                .build();
    }



}