package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonChkEnrDataRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonClmSalesRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonPrnDetailResp;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.entity.NFNV02Entity;
import com.fubon.ecplatformapi.model.entity.NFNV03Entity;
import com.fubon.ecplatformapi.repository.NFNV02Repository;
import com.fubon.ecplatformapi.repository.NFNV03Repository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@Slf4j
@Component
public class PolicyDetailMapper {
    private static NFNV02Repository nfnv02Repository;
    private static NFNV03Repository nfnv03Repository;

    @Autowired
    public PolicyDetailMapper(NFNV02Repository nfnv02Repository, NFNV03Repository nfnv03Repository) {
        PolicyDetailMapper.nfnv02Repository = nfnv02Repository;
        PolicyDetailMapper.nfnv03Repository = nfnv03Repository;
    }


    public static DetailResultVo.InsuranceInfo mapToInsuranceInfo(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
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
                        .relationPeople(ecAppInsure.getSecEcAppWsBean().getSecMtg())
                        .relationPeople2(ecAppInsure.getSecEcAppWsBean().getSecMtg2())
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
                                .insuredPlanCode("還沒弄")
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
                        .payer(sec.getSecMtg())
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
    public static DetailResultVo.InsuranceSubject mapToInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        FubonPolicyDetailRespDTO.MohEcAppWsBean moh = ecAppInsure.getMohEcAppWsBean();
        FubonPolicyDetailRespDTO.RskEcAppWsBean rsk = ecAppInsure.getRskEcAppWsBean();
        FubonPolicyDetailRespDTO.BudEcAppWsBean bud = ecAppInsure.getBudEcAppWsBean();
        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsure.getSecEcAppWsBean();
        FubonPolicyDetailRespDTO.EecEcAppWsBean eec = ecAppInsure.getEecEcAppWsBean();
        bud.setBudSpace(1.1);
        bud.setBudSpaceUnit("BudSpaceUnit");
        String result = bud.getBudSpace() + bud.getBudSpaceUnit();

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
                .buildSpace(result)
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
    public static DetailResultVo.EtpInsuranceSubject mapToEtpInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskEcAppEtpWsBean> rskEc = ecAppInsureEtp.getRskEcAppWsBeans();

        return DetailResultVo.EtpInsuranceSubject.builder()
                .content(String.valueOf(rskEc.stream()
                        .map(FubonPolicyDetailRespDTO.RskEcAppEtpWsBean::getContent)
                        .collect(Collectors.toList())))
                .desc(String.valueOf(rskEc.stream()
                        .map(FubonPolicyDetailRespDTO.RskEcAppEtpWsBean::getDesc)
                        .collect(Collectors.toList())))
                .build();
    }
    public static DetailResultVo.EtpInsuranceSubjectDetail mapToEtpInsuranceSubjectDetail(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean> rskDE = ecAppInsureEtp.getRskDEcAppWsBeans();

        return DetailResultVo.EtpInsuranceSubjectDetail.builder()
                .seq(Integer.parseInt(rskDE.stream()
                        .map(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean::getSeq)
                        .toList().toString().replaceAll("[\\[\\]]", "")))
                .values(rskDE.stream()
                        .map(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean::getValues)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .build();
    }
    public static DetailResultVo.InsuranceItem mapToInsuranceItem(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.PitEcAppWsBean> pit = ecAppInsure.getPitEcAppWsBeans();

        List<List<String>> listValues = new ArrayList<>();
        for (FubonPolicyDetailRespDTO.PitEcAppEtpWsBean pitEcAppEtpWsBean : ecAppInsureEtp.getPitEcAppEtpWsBeans()) {
            Collection<String> values = pitEcAppEtpWsBean.getValues();
            List<String> stringValues = new ArrayList<>(values);
            listValues.add(stringValues);
        }

        return DetailResultVo.InsuranceItem.builder()
                .bnfCode(pit.stream()
                        .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitBnfCode)
                        .collect(Collectors.joining()))
                .eb0Lists(mapToEb0Lists(ecAppInsure))
                .finalPrm(pit.stream()
                        .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitFinalprm)
                        .reduce(0.0, Double::sum))
                .pex(pit.stream()
                        .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitPex)
                        .reduce(0.0, Double::sum))
                .seq(Integer.parseInt(ecAppInsureEtp.getRskDEcAppWsBeans().stream()
                        .map(FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean::getSeq)
                        .toList().toString().replaceAll("[\\[\\]]", "")))
                .title(ecAppInsureEtp.getPitColumnNames().toString())
                .values(listValues)
                .build();
    }
    private static List<DetailResultVo.PitEb0List> mapToEb0Lists(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure){

        Collection<FubonPolicyDetailRespDTO.PitEcAppWsBean> pit = ecAppInsure.getPitEcAppWsBeans();

        return pit.stream()
                .flatMap(pitEcAppWsBean -> pitEcAppWsBean.getPitEb0Lists().stream())
                .map(fubonItem -> {
                    DetailResultVo.PitEb0List detailResultVoItem = new DetailResultVo.PitEb0List();
                    detailResultVoItem.setEb0TsiDesc(fubonItem.getEb0TsiDesc());
                    detailResultVoItem.setEb0TsiValue(fubonItem.getEb0TsiValue());
                    detailResultVoItem.setEb0TsiUnit(fubonItem.getEb0TsiUnit());
                    return detailResultVoItem;})
                .collect(Collectors.toList());
    }
    public static DetailResultVo.InsuranceList mapToInsuranceList(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        Collection<FubonPolicyDetailRespDTO.PitNecAppWsBeans> pitNec = ecAppInsure.getPitNEcAppWsBeans();

        return DetailResultVo.InsuranceList.builder()
                .bnfCode(pitNec.stream()
                        .map(pitNecAppWsBean -> pitNecAppWsBean.getPitBnfCode() + pitNecAppWsBean.getPitEb0Name())
                        .toList().toString())
                .name(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNIsrName)
                        .collect(Collectors.joining()))
                .uid(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNUid)
                        .collect(Collectors.joining()))
                .birthDate(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNBirth)
                        .collect(Collectors.joining()))
                .title(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNTitle)
                        .collect(Collectors.joining()))
                .relation(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNAppRelation)
                        .collect(Collectors.joining()))
                /* 個人險：先取得險種資料的險種序號(pitEcAppWsBean.pitSeq)後，再取得個人險受益人資料 (benEcAppWsBeans),
                條件：pitEcAppWsBean.pitSeq = benEcAppWsBeans.benPitseq & benType = P */
                .beneficiary(pitNec.stream()
                        .map(FubonPolicyDetailRespDTO.PitNecAppWsBeans::getPitNBeneficiary)
                        .collect(Collectors.joining()))
                .build();

    }
    public static DetailResultVo.InsuranceOtherList mapToInsuranceOtherList(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        List<FubonPolicyDetailRespDTO.MidEcAppWsBean> mid = ecAppInsure.getMidEcAppWsBeans();

        return DetailResultVo.InsuranceOtherList.builder()
                .petType(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidContent)
                        .findFirst().orElse(null))
                .petName(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidName)
                        .findFirst().orElse(null))
                .petSex(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidSex)
                        .findFirst().orElse(null))
                .petSeq(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidSeq)
                        .findFirst().orElse(null))
                .petBirthDate(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidBirdate)
                        .findFirst().orElse(null))
                .petAge(mid.stream()
                        .map(FubonPolicyDetailRespDTO.MidEcAppWsBean::getMidAge)
                        .findFirst().orElse(null))
                .build();
    }
    public static List<DetailResultVo.PolicyDeliveryRecord> mapToPolicyDeliveryRecord(FubonPrnDetailResp prnDetailResp) {
        List<FubonPrnDetailResp.PrnResult> prmList = prnDetailResp.getPrmList();
        FubonPrnDetailResp.PrnResult prnResult = prmList.stream().findFirst().orElse(null);

        if (prnResult != null) {
            return Collections.singletonList(DetailResultVo.PolicyDeliveryRecord.builder()
                    .prnDoc(prnResult.getPrnDoc())
                    .prmDocName(prnResult.getPrnDocName())
                    .prmFormat(prnResult.getPrnFormat())
                    .prmType(prnResult.getPrnType())
                    .prmSendType(prnResult.getPrnSendType())
                    .prmPrintStatus(prnResult.getPrnPrintStatus())
                    .prmPrintDate(prnResult.getPrnPrintdate())
                    .build());
        } else {
            log.warn("富邦保單寄送紀錄回應 is null");
            return null;
        }
    }
    public static DetailResultVo.UnpaidRecord mapToUnpaidRecord(String polyNo) {
        NFNV02Entity unpaidRecord = nfnv02Repository.findUnpaidByPolyno(polyNo);

        String effDate = unpaidRecord.getEffda().toInstant().toString() + " - " + unpaidRecord.getExpda().toInstant().toString();

        return DetailResultVo.UnpaidRecord.builder()
                .insType(unpaidRecord.getInslin())
                .policyNum(unpaidRecord.getPolynoQ())
                .quoteNum(unpaidRecord.getPolyno())
                .proposer(unpaidRecord.getIsrnm())
                .insured(unpaidRecord.getInsnm())
                .platno(unpaidRecord.getPlatno())
                .effdate(effDate)
                .premium(unpaidRecord.getPremiu())
                .build();

    }
    public static DetailResultVo.PaidRecord mapToPaidRecord(String polyNo) {
        NFNV03Entity paymentRecord = nfnv03Repository.findPaymentByPolyno(polyNo);
        String effDate = paymentRecord.getEffda().toInstant().toString() + " - " + paymentRecord.getExpda().toInstant().toString();

        return  DetailResultVo.PaidRecord.builder()
                .insType(paymentRecord.getInslin())
                .policyNum(paymentRecord.getPolynoQ())
                .quoteNum(paymentRecord.getPolyno())
                .proposer(paymentRecord.getIsrnm())
                .insured(paymentRecord.getInsnm())
                .platno("待確認")
                .effdate(effDate)
                .payamt(paymentRecord.getPayamt())
                .pcllDate(paymentRecord.getPcllda())
                .payKind(paymentRecord.getFundkind())
                .build();
    }

    public static List<DetailResultVo.ClaimRecord> mapToClaimRecord(FubonClmSalesRespDTO clmSalesResp) {
        List<FubonClmSalesRespDTO.Content.ClaimInfo> claimInfoList = clmSalesResp.getContent().getClaimInfo();
        return claimInfoList.stream()
                .map(claimInfo -> DetailResultVo.ClaimRecord.builder()
                        .insType(claimInfo.getQueryPlan())
                        .acdate(claimInfo.getAcdate())
                        .clamNo(claimInfo.getClamno())
                        .polyNo(claimInfo.getQueryPolyNo())
                        .insuredName(claimInfo.getIcname())
                        .accper(claimInfo.getAccper())
                        .clamStatus(claimInfo.getClamsts())
                        .prcdeptNm(claimInfo.getPrcdeptnm())
                        .prcempNm(claimInfo.getPrcempnm())
                        .phone("資料庫查詢")
                        .build())
                .collect(Collectors.toList());
    }

    public static DetailResultVo.ConservationRecord getConservationRecord(FubonChkEnrDataRespDTO chkEnrData) {
        Collection<FubonChkEnrDataRespDTO.RecEcAppWsBean> recEcAppWsBeans = chkEnrData.getRecEcAppWsBeans();
        if (recEcAppWsBeans != null && !recEcAppWsBeans.isEmpty()) {
            FubonChkEnrDataRespDTO.RecEcAppWsBean recEcAppWsBean = recEcAppWsBeans.iterator().next();
            return DetailResultVo.ConservationRecord.builder()
                    .proposerName(recEcAppWsBean.getRmaACliname())
                    .insuredName(recEcAppWsBean.getRmalCliname())
                    .policyName(recEcAppWsBean.getFormatid())
                    .createDate(recEcAppWsBean.getSecCdate())
                    .modifyDate(recEcAppWsBean.getSecAdate())
                    .wrpStatus(recEcAppWsBean.getSecWrpsts())
                    .closeDate(recEcAppWsBean.getCloseDate())
                    .build();
        } else {
            log.warn("富邦保全紀錄回應 is null");
            return null;
        }
    }
}
