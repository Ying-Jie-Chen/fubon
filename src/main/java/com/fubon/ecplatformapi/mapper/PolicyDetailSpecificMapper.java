package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.model.dto.CarInsuranceTermDTO;
import com.fubon.ecplatformapi.model.dto.PaymentRecordDTO;
import com.fubon.ecplatformapi.model.dto.UnpaidRecordDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonChkEnrDataRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonClmSalesRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonPrnDetailResp;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.entity.CarInsuranceTerm;
import com.fubon.ecplatformapi.repository.CarInsuranceTermRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class PolicyDetailSpecificMapper {
    private static CarInsuranceTermRepository carInsuranceTermRepository;
    private static JdbcTemplate jdbcTemplate;
    @Autowired
    public PolicyDetailSpecificMapper(JdbcTemplate jdbcTemplate, CarInsuranceTermRepository carInsuranceTermRepository) {
        PolicyDetailSpecificMapper.jdbcTemplate = jdbcTemplate;
        PolicyDetailSpecificMapper.carInsuranceTermRepository = carInsuranceTermRepository;
    }

    /**
     * 基本資料 (車、個險)
     */
    public static DetailResultVo.InsuranceInfo mapToInsuranceInfo(InsuranceType insType, FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsure.getSecEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmal = ecAppInsure.getRmalEcAppWsBean();
        List<FubonPolicyDetailRespDTO.EcoEcAppWsBeans> eco = ecAppInsure.getEcoEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmaA = ecAppInsure.getRmaAEcAppWsBean();
        FubonPolicyDetailRespDTO.CrdEcAppWsBean crd = ecAppInsure.getCrdEcAppWsBean();

        // 以名冊序號(ecoEcAppWsBean.ecoSeq)對應個人險受益人資料 (benEcAppWsBeans)：
        List<Integer> ecoSeqList = ecAppInsure.getEcoEcAppWsBean().stream()
                .map(FubonPolicyDetailRespDTO.EcoEcAppWsBeans::getEcoSeq)
                .toList();
        //對應條件：ecoEcAppWsBean.ecoSeq = benEcAppWsBeans.benRskSeq & benType = E
        List<FubonPolicyDetailRespDTO.BenEcAppWsBean> beneficiaries = ecAppInsure.getBenEcAppWsBeans().stream()
                .filter(benEcAppWsBean -> "E".equals(benEcAppWsBean.getBenType()) && ecoSeqList.contains(benEcAppWsBean.getBenRskSeq()))
                .toList();
        // 將搜集的結果設置 'beneficiary' 屬性
        String beneficiary = beneficiaries.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

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
                        .ptcptNo(String.valueOf(ecAppInsure.getEcoEcAppWsBean().stream()
                                .map(FubonPolicyDetailRespDTO.EcoEcAppWsBeans::getEcoSeq)
                                .min(Integer::compareTo).orElse(null))) // 取ecoSeq名冊序號最小的
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
                .insuredList(eco.stream().map(ecoEcAppWsBeans -> DetailResultVo.InsuredList.builder()
                                .insuredName(String.valueOf(ecoEcAppWsBeans.getEcoCname()))
                                .insuredId(String.valueOf(ecoEcAppWsBeans.getEcoUid()))
                                .insuredBirthDate(ecoEcAppWsBeans.getEcoBirdate())
                                .insuredAddr(rmal.getRmaAddr())
                                .insuredTel1(rmal.getRmaTel1())
                                .insuredTel2(rmal.getRmaTel2())
                                .insuredEmail(String.valueOf(ecoEcAppWsBeans.getEcoEmail()))
                                .insuredPhone(String.valueOf(ecoEcAppWsBeans.getEcoMob()))
                                .insuredCompany(String.valueOf(ecoEcAppWsBeans.getEcoCompnm()))
                                .insuredContent(String.valueOf(ecoEcAppWsBeans.getEcoContent()))
                                .insuredCrcGrop(String.valueOf(ecoEcAppWsBeans.getEcoCrcGrp()))
                                .insuredPlanCode(getPlanCNames(ecoEcAppWsBeans.getEcoPlnCode(), insType))
                                .insuredQualification(rmal.getRmaQualification())
                                .insuredRepresentative(rmal.getRmaRepresentative())
                                .insuredRepresentativeId(rmal.getRmaRepresentativeId())
                                .title(String.valueOf(ecoEcAppWsBeans.getEcoReltn()))
                                .beneficiary(beneficiary)
                                .age(ecoEcAppWsBeans.getEcoAge())
                                .build())
                        .collect(Collectors.toList()))
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
    public static String getPlanCNames(String ecoPlnCode, InsuranceType insType) {
        try {
            String sql = "SELECT PLN_CNAME FROM VW_EC_APP_PLNLIST WHERE PLN_CODE = ? AND PLN_CLS = ?";
            return jdbcTemplate.queryForObject(sql, String.class, ecoPlnCode, insType);
        } catch (EmptyResultDataAccessException e) {
            log.warn("VW_EC_APP_PLNLIST 表中查詢不到結果: " + e.getMessage());
            return null;
        }
    }

    /**
     * 基本資料 (企業險)
     */
    public static DetailResultVo.InsuranceInfo mapToEtpInsuranceInfo(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsureEtp.getSecEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmal = ecAppInsureEtp.getRmaIEcAppWsBean();
        FubonPolicyDetailRespDTO.RmaEcAppWsBean rmaA = ecAppInsureEtp.getRmaAEcAppWsBean();

        return DetailResultVo.InsuranceInfo.builder()
                .basicInfo(DetailResultVo.BasicInfo.builder()
                        .policyNum(sec.getSecFormatid())
                        .effectDateStart(sec.getSecEffdate())
                        .effectDateEnd(sec.getSecExpdate())
                        .cgrCode(ecAppInsureEtp.getCgrCode())
                        .clsCode(ecAppInsureEtp.getClsCode())
                        .pnCode(ecAppInsureEtp.getPlnCode())
                        .totalPremium100(ecAppInsureEtp.getTotalPremium100())
                        .ourshr(ecAppInsureEtp.getOurshr())
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
                        .build())
                // 要保人資訊
                .proposerInfo(DetailResultVo.ProposerInfo.builder()
                        .proposerName(rmaA.getRmaCliname())
                        .proposerId(rmaA.getRmaUid())
                        .proposerBirthDate(rmaA.getRmaPBirth())
                        .proposerAddr(rmaA.getRmaAddr())
                        .proposerTel1(rmaA.getRmaTel1())
                        .proposerTel2(rmaA.getRmaTel2())
                        .proposerEmail(rmaA.getRmaEmail())
                        .relation(rmaA.getRmaRela())
                        .proposerRepresentative(rmaA.getRmaRepresentative())
                        .build())
                .build();
    }

    /**
     * 保險標的 (車、個險)
     */
    public static DetailResultVo.InsuranceSubject mapToEcInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
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

    /**
     * 保險標的 (企業險)
     */
    public static List<DetailResultVo.EtpInsuranceSubject> mapToEtpInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskEcAppEtpWsBean> rskEc = ecAppInsureEtp.getRskEcAppWsBeans();
        return rskEc.stream().map(rskEcAppEtpWsBean -> DetailResultVo.EtpInsuranceSubject.builder()
                        .content(String.valueOf(rskEcAppEtpWsBean.getContent()))
                        .desc(String.valueOf(rskEcAppEtpWsBean.getDesc()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 企業險保險的明細 (企業險)
     */
    public static List<DetailResultVo.EtpInsuranceSubjectDetail> mapToEtpInsuranceSubjectDetail(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean> rskDE = ecAppInsureEtp.getRskDEcAppWsBeans();

        return rskDE.stream().map(rskDEcAppEtpWsBean -> DetailResultVo.EtpInsuranceSubjectDetail.builder()
                        .seq(Integer.parseInt(String.valueOf(rskDEcAppEtpWsBean.getSeq()).replaceAll("[\\[\\]]", "")))
                        .values((List<String>) rskDEcAppEtpWsBean.getValues())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 保險項目 (車、個險)
     */
    public static List<DetailResultVo.InsuranceItem> mapToEcInsuranceItem(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        Collection<FubonPolicyDetailRespDTO.PitEcAppWsBean> pit = ecAppInsure.getPitEcAppWsBeans();

        return pit.stream().map(pitEcAppWsBean -> DetailResultVo.InsuranceItem.builder()
                .bnfCode(pitEcAppWsBean.getPitBnfCode())
                .eb0Lists(mapToEb0Lists(ecAppInsure))
                .finalPrm(pitEcAppWsBean.getPitFinalprm())
                .pex(pitEcAppWsBean.getPitPex())
                .build())
                .collect(toList());
    }

    /**
     * 保險項目 (企業險)
     */
    public static List<DetailResultVo.InsuranceItem> mapToEtpInsuranceItem(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        List<List<String>> listValues = ecAppInsureEtp.getPitEcAppEtpWsBeans().stream()
                .map(pitEcAppEtpWsBean -> new ArrayList<>(pitEcAppEtpWsBean.getValues()))
                .collect(Collectors.toList());

        return ecAppInsureEtp.getPitColumnNames().stream()
                .map(pitColumnName -> DetailResultVo.InsuranceItem.builder()
                        .seq(Integer.parseInt(String.valueOf(ecAppInsureEtp.getRskDEcAppWsBeans().iterator().next().getSeq())))
                        .title(pitColumnName)
                        .values(listValues)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 附加條款 (車險)
     */
    public static List<DetailResultVo.AdditionalTerm> mapToAdditionalTerm(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        String mohPrmCode = ecAppInsure.getMohEcAppWsBean().getMohPrmCode();
        List<String> sbcMohParam1 = ecAppInsure.getSbcEcAppWsBeans().stream()
                .map(FubonPolicyDetailRespDTO.SbcEcAppWsBean::getSbcMohParam1)
                .toList();
        List<CarInsuranceTermDTO> insuranceTerms = getInsuranceTerms(mohPrmCode, sbcMohParam1);

        return insuranceTerms.stream()
                .map(term -> {
                    String[] contentArray = term.getTermInsContent1().split(";");
                    String value = "";

                    for (String content : contentArray) {
                        String[] keyValue = content.split(",");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim();
                            String contentValue = keyValue[1].trim();

                            if (key.equals(term.getTermInsApiParam1())) {
                                value = "型式 " + contentValue;
                                break;
                            }
                        }
                    }
                    return DetailResultVo.AdditionalTerm.builder()
                            .term(term.getTermInsName())
                            .value(value)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 保險項目-附加條款(車險)
     */
    public static List<CarInsuranceTermDTO> getInsuranceTerms(String mohPrmCode, List<String> sbcMohParam1) {
        List<CarInsuranceTerm> insuranceTerms = carInsuranceTermRepository
                .findByTermInsCodeAndTermInsApiParam1In(mohPrmCode, sbcMohParam1);

        return insuranceTerms.stream()
                .map(InsuranceEntityMapper::mapToCarInsuranceTermDTO)
                .collect(Collectors.toList());
    }

    /**
     * 保額
     */
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

    /**
     * 險種名冊 (車、個險)
     */
    public static List<DetailResultVo.InsuranceList> mapToInsuranceList(InsuranceType insType, FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        Collection<FubonPolicyDetailRespDTO.PitNecAppWsBeans> pitNec = ecAppInsure.getPitNEcAppWsBeans();

        return pitNec.stream().map(pitNecAppWsBean -> {
            DetailResultVo.InsuranceList.InsuranceListBuilder builder = DetailResultVo.InsuranceList.builder()
                    .bnfCode(pitNecAppWsBean.getPitBnfCode() + mapToEb0Lists(ecAppInsure).stream().findFirst()
                            .map(eb0List -> String.join("", eb0List.getEb0TsiDesc(), eb0List.getEb0TsiValue(), eb0List.getEb0TsiUnit())).orElse(null))
                    .name(pitNecAppWsBean.getPitNIsrName())
                    .uid(pitNecAppWsBean.getPitNUid())
                    .birthDate(pitNecAppWsBean.getPitNBirth())
                    .title(pitNecAppWsBean.getPitNTitle())
                    .relation(pitNecAppWsBean.getPitNAppRelation());

            if (InsuranceType.Personal_Insurance.contains(insType)) {
                // 取得險種資料的險種序號
                List<Integer> pitSeqList = ecAppInsure.getPitEcAppWsBeans().stream()
                        .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitSeq)
                        .toList();
                // 取得個人險受益人資料，條件：pitEcAppWsBean.pitSeq = benEcAppWsBeans.benPitseq & benType = P
                List<FubonPolicyDetailRespDTO.BenEcAppWsBean> personalBeneficiaries = ecAppInsure.getBenEcAppWsBeans().stream()
                        .filter(benEcAppWsBean -> "P".equals(benEcAppWsBean.getBenType()) && pitSeqList.contains(benEcAppWsBean.getBenSeq().toString()))
                        .toList();
                // 將搜集的結果設置 'beneficiary' 屬性
                String beneficiary = personalBeneficiaries.stream()
                        .findFirst().map(Object::toString).orElse(null);

                builder.beneficiary(beneficiary);

            } else {
                builder.beneficiary(pitNecAppWsBean.getPitNBeneficiary());
            }
            return builder.build();

        }).collect(Collectors.toList());

    }


    /**
     * 險種名冊-其他 (個險)
     */
    public static List<DetailResultVo.InsuranceOtherList> mapToInsuranceOtherList(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        List<FubonPolicyDetailRespDTO.MidEcAppWsBean> mid = ecAppInsure.getMidEcAppWsBeans();
        return mid.stream().map(midEcAppWsBean ->  DetailResultVo.InsuranceOtherList.builder()
                .petType(midEcAppWsBean.getMidContent())
                .petName(midEcAppWsBean.getMidName())
                .petSex(midEcAppWsBean.getMidSex())
                .petSeq(midEcAppWsBean.getMidSeq())
                .petBirthDate(midEcAppWsBean.getMidBirdate())
                .petAge(midEcAppWsBean.getMidAge())
                .build())
                .collect(toList());
    }

    /**
     * 保單寄送記錄 (車、個險)
     */
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

    /**
     * 未繳保費
     */
    public static List<DetailResultVo.UnpaidRecord> mapToUnpaidRecord(UnpaidRecordDTO unpaidRecord) {

        String effDate = unpaidRecord.getEffDa() + " - " + unpaidRecord.getExpDa();

        return Collections.singletonList(DetailResultVo.UnpaidRecord.builder()
                .insType(unpaidRecord.getInsLin())
                .policyNum(unpaidRecord.getPolyNoQ())
                .quoteNum(unpaidRecord.getPolyNo())
                .proposer(unpaidRecord.getIsrNm())
                .insured(unpaidRecord.getInsNm())
                .platno(unpaidRecord.getPlatNo())
                .effdate(effDate)
                .premium(unpaidRecord.getPreMiu())
                .build());

    }

    /**
     * 繳費紀錄
     */
    public static List<DetailResultVo.PaidRecord> mapToPaidRecord(PaymentRecordDTO paymentRecord) {

        String effDate = paymentRecord.getEffDa().toInstant().toString() + " - " + paymentRecord.getExpDa().toInstant().toString();

        return Collections.singletonList(DetailResultVo.PaidRecord.builder()
                .insType(paymentRecord.getInsLin())
                .policyNum(paymentRecord.getPolyNoQ())
                .quoteNum(paymentRecord.getPolyNo())
                .proposer(paymentRecord.getIsrNm())
                .insured(paymentRecord.getInsNm())
                .platno("待確認")
                .effdate(effDate)
                .payamt(paymentRecord.getPayAmt())
                .pcllDate(paymentRecord.getPcllDa())
                .payKind(paymentRecord.getFundKind())
                .build());
    }

    /**
     * 理賠紀錄
     */
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

    /**
     * 保全紀錄
     */
    public static List<DetailResultVo.ConservationRecord> getConservationRecord(FubonChkEnrDataRespDTO chkEnrData) {
        Collection<FubonChkEnrDataRespDTO.RecEcAppWsBean> recEcAppWsBeans = chkEnrData.getRecEcAppWsBeans();
        if (recEcAppWsBeans != null && !recEcAppWsBeans.isEmpty()) {
            FubonChkEnrDataRespDTO.RecEcAppWsBean recEcAppWsBean = recEcAppWsBeans.iterator().next();
            return Collections.singletonList(DetailResultVo.ConservationRecord.builder()
                    .proposerName(recEcAppWsBean.getRmaACliname())
                    .insuredName(recEcAppWsBean.getRmalCliname())
                    .policyName(recEcAppWsBean.getFormatid())
                    .createDate(recEcAppWsBean.getSecCdate())
                    .modifyDate(recEcAppWsBean.getSecAdate())
                    .wrpStatus(recEcAppWsBean.getSecWrpsts())
                    .closeDate(recEcAppWsBean.getCloseDate())
                    .build());
        } else {
            log.warn("富邦保全紀錄回應 is null");
            return null;
        }
    }
}
