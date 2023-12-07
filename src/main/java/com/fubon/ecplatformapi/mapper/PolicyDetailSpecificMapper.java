package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
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

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static com.fubon.ecplatformapi.enums.InsuranceType.Personal_Insurance;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class PolicyDetailSpecificMapper {
    private static DataSource dataSource;
    private static CarInsuranceTermRepository carInsuranceTermRepository;
    private static JdbcTemplate jdbcTemplate;
    @Autowired
    public PolicyDetailSpecificMapper(DataSource dataSource, JdbcTemplate jdbcTemplate, CarInsuranceTermRepository carInsuranceTermRepository) {
        PolicyDetailSpecificMapper.dataSource = dataSource;
        PolicyDetailSpecificMapper.jdbcTemplate = jdbcTemplate;
        PolicyDetailSpecificMapper.carInsuranceTermRepository = carInsuranceTermRepository;
    }


    /**
     * 基本資料 (車、個險)
     */
    public static DetailResultVo.InsuranceInfo mapToInsuranceInfo(InsuranceType insType, FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {

        try {

            if (ecAppInsure == null) {
                return null;
            }

            DetailResultVo.InsuranceInfo.InsuranceInfoBuilder builder = DetailResultVo.InsuranceInfo.builder();

            Optional<FubonPolicyDetailRespDTO.RmaEcAppWsBean> rmalEcAppWsBean = Optional.ofNullable(ecAppInsure.getRmalEcAppWsBean());
            Optional<FubonPolicyDetailRespDTO.RmaEcAppWsBean> rmaAEcAppWsBean = Optional.ofNullable(ecAppInsure.getRmaAEcAppWsBean());
            Optional<FubonPolicyDetailRespDTO.CrdEcAppWsBean> crdEcAppWsBean = Optional.ofNullable(ecAppInsure.getCrdEcAppWsBean());

            Optional.ofNullable(ecAppInsure.getSecEcAppWsBean()).ifPresent(sec -> builder
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
                            .isEIP(sec.getSecEip())
                            .fubonLifeAgree(sec.getSecFubonlifeAgree())
                            .ptcptNo(String.valueOf(
                                    ecAppInsure.getEcoEcAppWsBean() != null ? ecAppInsure.getEcoEcAppWsBean().stream()
                                            .map(FubonPolicyDetailRespDTO.EcoEcAppWsBeans::getEcoSeq)
                                            .min(Integer::compareTo).orElse(null) : null))
                            .relationPeople(sec.getSecMtg())
                            .relationPeople2(sec.getSecMtg2())
                            .build()));

            Optional.ofNullable(ecAppInsure.getRmalEcAppWsBean()).ifPresent(rmal -> builder
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
                            .build()));

            Optional.ofNullable(ecAppInsure.getSecEcAppWsBean()).ifPresent(sec -> {
                DetailResultVo.ProposerInfo.ProposerInfoBuilder proposerInfoBuilder = DetailResultVo.ProposerInfo.builder();
                rmaAEcAppWsBean.ifPresent(rmaA -> proposerInfoBuilder
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
                        .proposerRepresentative(rmaA.getRmaRepresentative())
                        .proposerRepresentativeId(rmaA.getRmaRepresentativeId()));

                crdEcAppWsBean.ifPresent(crd -> proposerInfoBuilder
                        .cardNo(crd.getCrdCardno())
                        .cardExpDate(crd.getCrdExpdate()));

                builder.proposerInfo(proposerInfoBuilder.build());
            });

            Optional.ofNullable(ecAppInsure.getFliEcAppWsBeans()).ifPresent(fliBeans -> {
                List<DetailResultVo.FlightInfo> flightInfoList = fliBeans.stream()
                        .map(fliEcAppWsBean -> DetailResultVo.FlightInfo.builder()
                                .flightSeq(fliEcAppWsBean.getFliRskSeq())
                                .flightCode(fliEcAppWsBean.getFliFlightType())
                                .flightNo(fliEcAppWsBean.getFliFlightCode() + fliEcAppWsBean.getFliFlightNo())
                                .flightDate(fliEcAppWsBean.getFliFlightDate())
                                .flightCity(fliEcAppWsBean.getFliFlightCty())
                                .build())
                        .collect(Collectors.toList());

                builder.flightInfo(flightInfoList);
            });

            rmalEcAppWsBean.ifPresent(rmal -> {
                if (Personal_Insurance.contains(insType)) {
                    List<DetailResultVo.InsuredList> insuredList = Optional.ofNullable(ecAppInsure.getEcoEcAppWsBean())
                            .map(ecoEcAppBeans -> ecoEcAppBeans.stream()
                                    .map(ecoEcAppWsBeans -> DetailResultVo.InsuredList.builder()
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
                                            .beneficiary(getBeneficiary(ecAppInsure))
                                            .age(ecoEcAppWsBeans.getEcoAge())
                                            .build())
                                    .collect(Collectors.toList()))
                            .orElse(Collections.emptyList());

                    builder.insuredList(insuredList);
                }});

            return DetailResultVo.InsuranceInfo.builder().build();

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getBeneficiary(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure){
        List<Integer> ecoSeqList = Optional.ofNullable(ecAppInsure.getEcoEcAppWsBean())
                .map(ecoEcAppWsBeans -> ecoEcAppWsBeans.stream()
                        .map(FubonPolicyDetailRespDTO.EcoEcAppWsBeans::getEcoSeq)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<FubonPolicyDetailRespDTO.BenEcAppWsBean> beneficiaries = Optional.ofNullable(ecAppInsure.getBenEcAppWsBeans())
                .map(benEcAppWsBeans -> benEcAppWsBeans.stream()
                        .filter(benEcAppWsBean -> "E".equals(benEcAppWsBean.getBenType()) && ecoSeqList.contains(benEcAppWsBean.getBenRskSeq()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        return beneficiaries.stream()
                .findFirst().map(Object::toString).orElse(null);    }

    public static String getPlanCNames(String ecoPlnCode, InsuranceType insType) {
        try {
            String sql = "SELECT PLN_CNAME FROM VW_EC_APP_PLNLIST WHERE PLN_CODE = ? AND PLN_CLS = ?";
            return jdbcTemplate.queryForObject(sql, String.class, ecoPlnCode, insType);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Itzel| VW_EC_APP_PLNLIST 表中查詢不到結果: " + e.getMessage());
            log.warn("VW_EC_APP_PLNLIST 表中查詢不到結果: " + e.getMessage());
            return null;
        }
    }

    /**
     * 基本資料 (企業險)
     */
    public static DetailResultVo.InsuranceInfo mapToEtpInsuranceInfo(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        DetailResultVo.BasicInfo basicInfo = Optional.ofNullable(ecAppInsureEtp.getSecEcAppWsBean())
                .map(sec -> DetailResultVo.BasicInfo.builder()
                        .policyNum(sec.getSecFormatid())
                        .effectDateStart(sec.getSecEffdate())
                        .effectDateEnd(sec.getSecExpdate())
                        .cgrCode(ecAppInsureEtp.getCgrCode())
                        .clsCode(ecAppInsureEtp.getClsCode())
                        .pnCode(ecAppInsureEtp.getPlnCode())
                        .totalPremium100(ecAppInsureEtp.getTotalPremium100())
                        .ourshr(ecAppInsureEtp.getOurshr())
                        .build())
                .orElse(DetailResultVo.BasicInfo.builder().build());
        // 被保險人資訊
        DetailResultVo.InsuredInfo insuredInfo = Optional.ofNullable(ecAppInsureEtp.getRmaIEcAppWsBean())
                .map(rml -> DetailResultVo.InsuredInfo.builder()
                        .insuredName(rml.getRmaCliname())
                        .insuredId(rml.getRmaUid())
                        .insuredBirthDate(rml.getRmaPBirth())
                        .insuredAddr(rml.getRmaAddr())
                        .insuredTel1(rml.getRmaTel1())
                        .insuredTel2(rml.getRmaTel2())
                        .insuredEmail(rml.getRmaEmail())
                        .insuredPhone(rml.getRmaMobTel())
                        .build())
                .orElse(DetailResultVo.InsuredInfo.builder().build());
        // 要保人資訊
        DetailResultVo.ProposerInfo proposerInfo = Optional.ofNullable(ecAppInsureEtp.getRmaAEcAppWsBean())
                .map(rmA -> DetailResultVo.ProposerInfo.builder()
                        .proposerName(rmA.getRmaCliname())
                        .proposerId(rmA.getRmaUid())
                        .proposerBirthDate(rmA.getRmaPBirth())
                        .proposerAddr(rmA.getRmaAddr())
                        .proposerTel1(rmA.getRmaTel1())
                        .proposerTel2(rmA.getRmaTel2())
                        .proposerEmail(rmA.getRmaEmail())
                        .relation(rmA.getRmaRela())
                        .proposerRepresentative(rmA.getRmaRepresentative())
                        .build())
                .orElse(DetailResultVo.ProposerInfo.builder().build());

        return DetailResultVo.InsuranceInfo.builder()
                .basicInfo(basicInfo)
                .insuredInfo(insuredInfo)
                .proposerInfo(proposerInfo)
                .build();
    }

    /**
     * 保險標的 (車、個險)
     */
//    public static DetailResultVo.InsuranceSubject mapToEcInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
//        FubonPolicyDetailRespDTO.MohEcAppWsBean moh = ecAppInsure.getMohEcAppWsBean();
//        FubonPolicyDetailRespDTO.RskEcAppWsBean rsk = ecAppInsure.getRskEcAppWsBean();
//        FubonPolicyDetailRespDTO.BudEcAppWsBean bud = ecAppInsure.getBudEcAppWsBean();
//        FubonPolicyDetailRespDTO.SecEcAppWsBean sec = ecAppInsure.getSecEcAppWsBean();
//        FubonPolicyDetailRespDTO.EecEcAppWsBean eec = ecAppInsure.getEecEcAppWsBean();
//
//        String result = bud.getBudSpace() + bud.getBudSpaceUnit();
//
//        return  DetailResultVo.InsuranceSubject.builder()
//                .plateNo(moh.getMohPlatno())
//                .plateCode(moh.getMohMotCode())
//                .lisnDate(moh.getMohLisndate())
//                .leaveFactoryYearDate(moh.getMohBuidyr())
//                .displa(moh.getMohDispla())
//                .peopno1(moh.getMohPeopno1())
//                .engineNo(moh.getMohEngnno())
//                .vehTramak(moh.getMohVehTramak())
//                .subjectAddr(rsk.getRskLocation())
//                .buildYear(bud.getBudBuildyear())
//                .buildOccp(bud.getBudOccp())
//                .buildRoof(bud.getBudRoof())
//                .buildMaterial(bud.getBudMaterial())
//                .buildConstr(bud.getBudConstr())
//                .buildSection(rsk.getRskSection())
//                .buildHeight(bud.getBudHeight())
//                .buildSpace(result)
//                .mortgagee(ecAppInsure.getRmaMEcAPpWsBeans() != null ?
//                        ecAppInsure.getRmaMEcAPpWsBeans().stream()
//                                .map(FubonPolicyDetailRespDTO.RmaMEcAppWsBean::getRmaCliname)
//                                .collect(toList()):null)
//                .buildFloor(bud.getBudFlrType())
//                .buildAddFeeFlag(bud.getBudAddfeeMark())
//                .totalNum(sec.getSecTotalnum())
//                .travelCity1(sec.getSecCty1())
//                .travelCity2(sec.getSecCty2())
//                .travelCity3(sec.getSecCty3())
//                .travelCity4(sec.getSecCty4())
//                .mountainLocation(eec.getEecMountain())
//                .activityLoaction(eec.getEecActivity())
//                .build();
//    }

    public static DetailResultVo.InsuranceSubject mapToEcInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        return Optional.ofNullable(ecAppInsure)
                .map(insure -> { DetailResultVo.InsuranceSubject.InsuranceSubjectBuilder builder = DetailResultVo.InsuranceSubject.builder();

                    Optional.ofNullable(insure.getMohEcAppWsBean()).ifPresent(moh -> {
                        builder
                                .plateNo(moh.getMohPlatno())
                                .plateCode(moh.getMohMotCode())
                                .lisnDate(moh.getMohLisndate())
                                .leaveFactoryYearDate(moh.getMohBuidyr())
                                .displa(moh.getMohDispla())
                                .peopno1(moh.getMohPeopno1())
                                .engineNo(moh.getMohEngnno())
                                .vehTramak(moh.getMohVehTramak());
                    });

                    Optional.ofNullable(insure.getRskEcAppWsBean()).ifPresent(rsk -> {
                        builder
                                .subjectAddr(rsk.getRskLocation())
                                .buildSection(rsk.getRskSection());
                    });

                    Optional.ofNullable(insure.getSecEcAppWsBean()).ifPresent(sec -> {
                        builder
                                .totalNum(sec.getSecTotalnum())
                                .travelCity1(sec.getSecCty1())
                                .travelCity2(sec.getSecCty2())
                                .travelCity3(sec.getSecCty3())
                                .travelCity4(sec.getSecCty4());
                    });

                    Optional.ofNullable(insure.getBudEcAppWsBean()).ifPresent(bud -> {
                        String result = bud.getBudSpace() + bud.getBudSpaceUnit();
                        builder
                                .buildYear(bud.getBudBuildyear())
                                .buildOccp(bud.getBudOccp())
                                .buildRoof(bud.getBudRoof())
                                .buildSpace(result)
                                .buildMaterial(bud.getBudMaterial())
                                .buildConstr(bud.getBudConstr())
                                .buildHeight(bud.getBudHeight())
                                .buildFloor(bud.getBudFlrType())
                                .buildAddFeeFlag(bud.getBudAddfeeMark());
                    });

                    Optional.ofNullable(insure.getEecEcAppWsBean()).ifPresent(eec -> {
                        builder
                                .mountainLocation(eec.getEecMountain())
                                .activityLoaction(eec.getEecActivity());
                    });

                    builder
                            .mortgagee(ecAppInsure.getRmaMEcAPpWsBeans() != null ?
                                    ecAppInsure.getRmaMEcAPpWsBeans().stream()
                                            .map(FubonPolicyDetailRespDTO.RmaMEcAppWsBean::getRmaCliname)
                                            .collect(toList()) : null);

                    return builder.build();
                })
                .orElse(null);
    }

    /**
     * 保險標的 (企業險)
     */
    public static List<DetailResultVo.EtpInsuranceSubject> mapToEtpInsuranceSubject(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {
        Collection<FubonPolicyDetailRespDTO.RskEcAppEtpWsBean> rskEc = Optional.ofNullable(ecAppInsureEtp.getRskEcAppWsBeans()).orElseGet(Collections::emptyList);

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
        Collection<FubonPolicyDetailRespDTO.RskDEcAppEtpWsBean> rskDE = Optional.ofNullable(ecAppInsureEtp.getRskDEcAppWsBeans()).orElseGet(Collections::emptyList);

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
        Collection<FubonPolicyDetailRespDTO.PitEcAppWsBean> pit = Optional.ofNullable(ecAppInsure.getPitEcAppWsBeans()).orElseGet(Collections::emptyList);

        return pit.stream()
                .map(pitEcAppWsBean -> {
                    String pitPexUnit = pitEcAppWsBean.getPitPexUnit();
                    Double pex = pitEcAppWsBean.getPitPex() + (pitPexUnit != null && !pitPexUnit.isEmpty() ? Double.parseDouble(pitPexUnit):0.0);
                    List<DetailResultVo.PitEb0List> eb0Lists = mapToEb0Lists(pitEcAppWsBean);
                    return DetailResultVo.InsuranceItem.builder()
                            .bnfCode(pitEcAppWsBean.getPitBnfCode() + pitEcAppWsBean.getPitEb0Name())
                            .eb0Lists(eb0Lists)
                            .finalPrm(pitEcAppWsBean.getPitFinalprm())
                            .pex(pex)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 保險項目 (企業險)
     */
    public static List<DetailResultVo.InsuranceItem> mapToEtpInsuranceItem(FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp) {

        return Optional.ofNullable(ecAppInsureEtp.getPitEcAppEtpWsBeans())
                .map(insureEtpList -> {
                    List<List<String>> listValues = insureEtpList.stream()
                            .map(pitEcAppEtpWsBean -> new ArrayList<>(pitEcAppEtpWsBean.getValues()))
                            .collect(Collectors.toList());

                    List<String> listTile = insureEtpList.stream()
                            .map(bean -> Collections.singletonList(bean.getPitRskType()))
                            .flatMap(List::stream)
                            .toList();

                    return Optional.ofNullable(ecAppInsureEtp.getPitEcAppEtpWsBeans())
                            .map(pitEcAppEtpWsBeans -> pitEcAppEtpWsBeans.stream()
                                    .findFirst()
                                    .map(pitEcAppEtpWsBean -> DetailResultVo.InsuranceItem.builder()
                                            .seq(Integer.parseInt(String.valueOf(pitEcAppEtpWsBean.getPitRskSeq())))
                                            .title(listTile)
                                            .values(listValues)
                                            .build())
                                    .map(Collections::singletonList)
                                    .orElse(Collections.emptyList()))
                            .orElse(Collections.emptyList());
                })
                .orElse(Collections.emptyList());
    }

    /**
     * 附加條款 (車險)
     */
    public static List<DetailResultVo.AdditionalTerm> mapToAdditionalTerm(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
        String mohPrmCode = ecAppInsure.getMohEcAppWsBean().getMohPrmCode();
        List<String> sbcMohParam1 = Optional.ofNullable(ecAppInsure.getSbcEcAppWsBeans()).orElse(Collections.emptyList())
                .stream()
                .map(sbcEcAppWsBean -> Optional.ofNullable(sbcEcAppWsBean)
                        .map(FubonPolicyDetailRespDTO.SbcEcAppWsBean::getSbcMohParam1)
                        .orElse(null)).filter(Objects::nonNull).collect(toList());

        try {
            List<CarInsuranceTermDTO> insuranceTerms = getInsuranceTerms(mohPrmCode, sbcMohParam1);
            return insuranceTerms.stream()
                    .map(term -> {
                        String value = Arrays.stream(term.getTermInsContent1().split(";"))
                                .map(content -> content.split(","))
                                .filter(keyValue -> keyValue.length == 2)
                                .filter(keyValue -> keyValue[0].trim().equals(term.getTermInsApiParam1()))
                                .findFirst()
                                .map(keyValue -> "型式 " + keyValue[1].trim())
                                .orElse("");

                        return DetailResultVo.AdditionalTerm.builder()
                                .term(term.getTermInsName())
                                .value(value)
                                .build();
                    }).collect(Collectors.toList());

        } catch (Exception e) {
            log.debug("附加條款 (車險) 查無資料");
            return Collections.emptyList();
        }
    }

    /**
     * 保險項目-附加條款(車險)
     */
    public static List<CarInsuranceTermDTO> getInsuranceTerms(String mohPrmCode, List<String> sbcMohParam1) {

        List<CarInsuranceTerm> insuranceTerms = carInsuranceTermRepository
                .findByTermInsCodeAndTermInsApiParam1In(mohPrmCode, sbcMohParam1);

        if (insuranceTerms.isEmpty()) {
            log.debug("保險項目-附加條款(車險) 查詢不到資料");
            return Collections.emptyList();
        }
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
                .filter(Objects::nonNull)
                .flatMap(pitEcAppWsBean -> {
                    Collection<FubonPolicyDetailRespDTO.PitEb0List> pitEb0Lists = pitEcAppWsBean.getPitEb0Lists();
                    return pitEb0Lists != null ? pitEb0Lists.stream() : Stream.empty();
                })
                .map(fubonItem -> {
                    DetailResultVo.PitEb0List detailResultVoItem = new DetailResultVo.PitEb0List();
                    detailResultVoItem.setEb0TsiDesc(fubonItem.getEb0TsiDesc());
                    detailResultVoItem.setEb0TsiValue(fubonItem.getEb0TsiValue());
                    detailResultVoItem.setEb0TsiUnit(fubonItem.getEb0TsiUnit());
                    return detailResultVoItem;
                })
                .collect(Collectors.toList());
    }

    /**
     * 保額
     */
    private static List<DetailResultVo.PitEb0List> mapToEb0Lists(FubonPolicyDetailRespDTO.PitEcAppWsBean pitEcAppWsBean) {
        Collection<FubonPolicyDetailRespDTO.PitEb0List> pitEb0Lists = Optional.ofNullable(pitEcAppWsBean.getPitEb0Lists()).orElseGet(Collections::emptyList);

        return pitEb0Lists.stream()
                .map(fubonItem -> {
                    DetailResultVo.PitEb0List detailResultVoItem = new DetailResultVo.PitEb0List();
                    detailResultVoItem.setEb0TsiDesc(fubonItem.getEb0TsiDesc());
                    detailResultVoItem.setEb0TsiValue(fubonItem.getEb0TsiValue());
                    detailResultVoItem.setEb0TsiUnit(fubonItem.getEb0TsiUnit());
                    return detailResultVoItem;
                })
                .collect(Collectors.toList());
    }

    /**
     * 險種名冊 (車、個險)
     */
    public static List<DetailResultVo.InsuranceList> mapToInsuranceList(InsuranceType insType, FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
//        Collection<FubonPolicyDetailRespDTO.PitNecAppWsBeans> pitNec = ecAppInsure.getPitNEcAppWsBeans();
//
//        return pitNec.stream().map(pitNecAppWsBean -> {
//            DetailResultVo.InsuranceList.InsuranceListBuilder builder = DetailResultVo.InsuranceList.builder()
//                    .bnfCode(pitNecAppWsBean.getPitBnfCode() + mapToEb0Lists(ecAppInsure).stream().findFirst()
//                            .map(eb0List -> String.join("", eb0List.getEb0TsiDesc(), eb0List.getEb0TsiValue(), eb0List.getEb0TsiUnit())).orElse(null))
//                    .name(pitNecAppWsBean.getPitNIsrName())
//                    .uid(pitNecAppWsBean.getPitNUid())
//                    .birthDate(pitNecAppWsBean.getPitNBirth())
//                    .title(pitNecAppWsBean.getPitNTitle())
//                    .relation(pitNecAppWsBean.getPitNAppRelation());
//
//            if (Personal_Insurance.contains(insType)) {
//                List<Integer> pitSeqList = ecAppInsure.getPitEcAppWsBeans().stream()
//                        .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitSeq)
//                        .collect(toList());
//                List<FubonPolicyDetailRespDTO.BenEcAppWsBean> personalBeneficiaries = ecAppInsure.getBenEcAppWsBeans().stream()
//                        .filter(benEcAppWsBean -> "P".equals(benEcAppWsBean.getBenType()) && pitSeqList.contains(benEcAppWsBean.getBenSeq()))
//                        .collect(toList());
//                String beneficiary = personalBeneficiaries.stream()
//                        .findFirst().map(Object::toString).orElse(null);
//
//                builder.beneficiary(beneficiary);
//
//            } else {
//                builder.beneficiary(pitNecAppWsBean.getPitNBeneficiary());
//            }
//            return builder.build();
//
//        }).collect(Collectors.toList());
        return Optional.ofNullable(ecAppInsure.getPitNEcAppWsBeans()).orElse(Collections.emptyList())
                .stream().map(pitNecAppWsBean -> {DetailResultVo.InsuranceList.InsuranceListBuilder builder = DetailResultVo.InsuranceList.builder()
                        .bnfCode(pitNecAppWsBean.getPitBnfCode() + mapToEb0Lists(ecAppInsure).stream().findFirst()
                                .map(eb0List -> String.join("", eb0List.getEb0TsiDesc(), eb0List.getEb0TsiValue(), eb0List.getEb0TsiUnit()))
                                .orElse(null))
                        .name(pitNecAppWsBean.getPitNIsrName())
                        .uid(pitNecAppWsBean.getPitNUid())
                        .birthDate(pitNecAppWsBean.getPitNBirth())
                        .title(pitNecAppWsBean.getPitNTitle())
                        .relation(pitNecAppWsBean.getPitNAppRelation());

                    if (Personal_Insurance.contains(insType)) {
                        List<Integer> pitSeqList = Optional.ofNullable(ecAppInsure.getPitEcAppWsBeans())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(FubonPolicyDetailRespDTO.PitEcAppWsBean::getPitSeq)
                                .collect(toList());
                        List<FubonPolicyDetailRespDTO.BenEcAppWsBean> personalBeneficiaries = Optional.ofNullable(ecAppInsure.getBenEcAppWsBeans())
                                .orElse(Collections.emptyList())
                                .stream()
                                .filter(benEcAppWsBean -> "P".equals(benEcAppWsBean.getBenType()) && pitSeqList.contains(benEcAppWsBean.getBenSeq()))
                                .collect(toList());
                        String beneficiary = personalBeneficiaries.stream()
                                .findFirst().map(Object::toString).orElse(null);

                        builder.beneficiary(beneficiary);

                    } else {
                        builder.beneficiary(pitNecAppWsBean.getPitNBeneficiary());
                    }
                    return builder.build();

                })
                .collect(Collectors.toList());
    }

    /**
     * 險種名冊-其他 (個險)
     */
    public static List<DetailResultVo.InsuranceOtherList> mapToInsuranceOtherList(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure) {
//        List<FubonPolicyDetailRespDTO.MidEcAppWsBean> mid = ecAppInsure.getMidEcAppWsBeans();
//        return mid.stream().map(midEcAppWsBean ->  DetailResultVo.InsuranceOtherList.builder()
//                        .petType(midEcAppWsBean.getMidContent())
//                        .petName(midEcAppWsBean.getMidName())
//                        .petSex(midEcAppWsBean.getMidSex())
//                        .petSeq(midEcAppWsBean.getMidSeq())
//                        .petBirthDate(midEcAppWsBean.getMidBirdate())
//                        .petAge(midEcAppWsBean.getMidAge())
//                        .build())
//                .collect(toList());
        return Optional.ofNullable(ecAppInsure.getMidEcAppWsBeans()).orElse(Collections.emptyList())
                .stream().map(midEcAppWsBean -> DetailResultVo.InsuranceOtherList.builder()
                        .petType(midEcAppWsBean.getMidContent())
                        .petName(midEcAppWsBean.getMidName())
                        .petSex(midEcAppWsBean.getMidSex())
                        .petSeq(midEcAppWsBean.getMidSeq())
                        .petBirthDate(midEcAppWsBean.getMidBirdate())
                        .petAge(midEcAppWsBean.getMidAge())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 保單寄送記錄 (車、個險)
     */
    public static List<DetailResultVo.PolicyDeliveryRecord> mapToPolicyDeliveryRecord(FubonPrnDetailResp prnDetailResp) {
//        List<FubonPrnDetailResp.PrnResult> prmList = prnDetailResp.getPrmList();
//        FubonPrnDetailResp.PrnResult prnResult = prmList.stream().findFirst().orElse(null);
//
//        if (prnResult != null) {
//            return Collections.singletonList(DetailResultVo.PolicyDeliveryRecord.builder()
//                    .prmDocName(prnResult.getPrnDocName())
//                    .prmFormat(prnResult.getPrnFormat())
//                    .prmType(prnResult.getPrnType())
//                    .prmSendType(prnResult.getPrnSendType())
//                    .prmPrintStatus(prnResult.getPrnPrintStatus())
//                    .prmPrintDate(prnResult.getPrnPrintdate())
//                    .build());
//        } else {
//            log.debug("富邦保單寄送紀錄回應 is null");
//            return null;
//        }
        return Optional.ofNullable(prnDetailResp.getPrmList()).orElse(Collections.emptyList())
                .stream().findFirst()
                .map(prnResult -> Collections.singletonList(DetailResultVo.PolicyDeliveryRecord.builder()
                        .prmDocName(prnResult.getPrnDocName())
                        .prmFormat(prnResult.getPrnFormat())
                        .prmType(prnResult.getPrnType())
                        .prmSendType(prnResult.getPrnSendType())
                        .prmPrintStatus(prnResult.getPrnPrintStatus())
                        .prmPrintDate(prnResult.getPrnPrintdate())
                        .build()))
                .orElseGet(() -> {
                    log.debug("富邦保單寄送紀錄回應 is null");
                    return Collections.emptyList();
                });
    }

    /**
     * 未繳保費
     */
    public static List<DetailResultVo.UnpaidRecord> mapToUnpaidRecord(UnpaidRecordDTO unpaidRecord) {
        System.out.println("Itzel | UnPaidRecord: " + unpaidRecord);

        if (unpaidRecord != null) {

            String effDate = unpaidRecord.getEffDa() + " + " + unpaidRecord.getExpDa();

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
        }else { return Collections.singletonList(DetailResultVo.UnpaidRecord.builder().build()); }
    }

    /**
     * 繳費紀錄
     */
    public static List<DetailResultVo.PaidRecord> mapToEcPaidRecord(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, PaymentRecordDTO paymentRecord) {
        System.out.println("Itzel | PaidRecord : " + paymentRecord);

        if(paymentRecord != null){
            String effDate = paymentRecord.getEffDa().toInstant().toString() + " + " + paymentRecord.getExpDa().toInstant().toString();
            return Collections.singletonList(DetailResultVo.PaidRecord.builder()
                    .insType(paymentRecord.getInsLin())
                    .policyNum(paymentRecord.getPolyNoQ())
                    .quoteNum(paymentRecord.getPolyNo())
                    .proposer(paymentRecord.getIsrNm())
                    .insured(paymentRecord.getInsNm())
                    .platno(ecAppInsure.getMohEcAppWsBean().getMohPlatno())
                    .effdate(effDate)
                    .payamt(paymentRecord.getPayAmt())
                    .pcllDate(paymentRecord.getPcllDa())
                    .payKind(paymentRecord.getFundKind())
                    .build());
        }else { return Collections.singletonList(DetailResultVo.PaidRecord.builder().build()); }
    }

    public static List<DetailResultVo.PaidRecord> mapToEtpPaidRecord(PaymentRecordDTO paymentRecord) {
        System.out.println("Itzel | PaidRecord : " + paymentRecord);

        if(paymentRecord != null){
            String effDate = paymentRecord.getEffDa().toInstant().toString() + " + " + paymentRecord.getExpDa().toInstant().toString();

            return Collections.singletonList(DetailResultVo.PaidRecord.builder()
                    .insType(paymentRecord.getInsLin())
                    .policyNum(paymentRecord.getPolyNoQ())
                    .quoteNum(paymentRecord.getPolyNo())
                    .proposer(paymentRecord.getIsrNm())
                    .insured(paymentRecord.getInsNm())
                    .effdate(effDate)
                    .payamt(paymentRecord.getPayAmt())
                    .pcllDate(paymentRecord.getPcllDa())
                    .payKind(paymentRecord.getFundKind())
                    .build());
        }else {
            return Collections.singletonList(DetailResultVo.PaidRecord.builder().build());
        }
    }


    /**
     * 理賠紀錄
     */
    public static List<DetailResultVo.ClaimRecord> mapToClaimRecord(String policyNum, FubonClmSalesRespDTO clmSalesResp) {
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
                        .phone(claimInfo.getPhone())
                        .payDate(getClaimDate(claimInfo.getClamno(), policyNum))
                        .build())
                .collect(Collectors.toList());
    }

    private static LocalDateTime getClaimDate(String claimNo, String policyNo) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT MAX(d00view.paydate) AS max_paydate " + "FROM fin.find00_view d00view " + "WHERE d00view.clamno = ? " + "AND d00view.status = '1' " + "AND d00view.polyno = ? " + "AND d00view.payacc = '01'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, claimNo);
                preparedStatement.setString(2, policyNo);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Timestamp sqlTimestamp = resultSet.getTimestamp("max_paydate");
                        if (sqlTimestamp != null) { return sqlTimestamp.toLocalDateTime(); }
                    }
                }
            }
        } catch (SQLException e) {
            log.debug("** SQLException : Method getClaimDate : ");
            e.printStackTrace();
            throw new CustomException(e.getMessage(), StatusCodeEnum.ERR00999.getCode());
        }
        return null;
    }

    /**
     * 保全紀錄
     */
    public static List<DetailResultVo.ConservationRecord> getConservationRecord(FubonChkEnrDataRespDTO chkEnrData) {
//        Collection<FubonChkEnrDataRespDTO.RecEcAppWsBean> recEcAppWsBeans = chkEnrData.getRecEcAppWsBeans();
//        if (recEcAppWsBeans != null && !recEcAppWsBeans.isEmpty()) {
//            FubonChkEnrDataRespDTO.RecEcAppWsBean recEcAppWsBean = recEcAppWsBeans.iterator().next();
//            return Collections.singletonList(DetailResultVo.ConservationRecord.builder()
//                    .proposerName(recEcAppWsBean.getRmaACliname())
//                    .insuredName(recEcAppWsBean.getRmalCliname())
//                    .policyName(recEcAppWsBean.getFormatid())
//                    .createDate(recEcAppWsBean.getSecCdate())
//                    .modifyDate(recEcAppWsBean.getSecAdate())
//                    .wrpStatus(recEcAppWsBean.getSecWrpsts())
//                    .closeDate(recEcAppWsBean.getCloseDate())
//                    .build());
//        } else {
//            log.warn("富邦保全紀錄回應 is null");
//            return null;
//        }
        return Optional.ofNullable(chkEnrData.getRecEcAppWsBeans())
                .map(recEcAppWsBeans -> recEcAppWsBeans.stream()
                        .findFirst()
                        .map(recEcAppWsBean -> Collections.singletonList(DetailResultVo.ConservationRecord.builder()
                                .proposerName(recEcAppWsBean.getRmaACliname())
                                .insuredName(recEcAppWsBean.getRmalCliname())
                                .policyName(recEcAppWsBean.getFormatid())
                                .createDate(recEcAppWsBean.getSecCdate())
                                .modifyDate(recEcAppWsBean.getSecAdate())
                                .wrpStatus(recEcAppWsBean.getSecWrpsts())
                                .closeDate(recEcAppWsBean.getCloseDate())
                                .build()))
                        .orElse(Collections.emptyList()))
                .orElseGet(() -> {
                    System.out.println(" Itzel | 富邦保全紀錄回應 is null");
                    return Collections.emptyList();
                });
    }
}
