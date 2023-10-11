package com.fubon.ecplatformapi.model.dto.vo;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class DetailResultVO {
    private InsuranceInfo insuranceInfo;
    private InsuranceSubject insuranceSubject;
    private List<EtpInsuranceSubject> etpInsuranceSubject;
    private List<EtpInsuranceSubjectDetail> etpInsuranceSubjectDetail;
    private List<InsuranceItem> insuranceItem;
    private List<InsuranceList> insuranceList;
    private List<PolicyDeliveryRecord> policyDeliveryRecord;
    private List<UnpaidRecord> unpaidRecord;
    private List<PaidRecord> paidRecord;
    private List<ClaimRecord> claimRecord;
    private List<ConservationRecord> conservationRecord;

    @Data
    public static class InsuranceInfo {
        private BasicInfo basicInfo;
        private InsuredInfo insuredInfo;
        private List<InsuredList> insuredList;
        private ProposerInfo proposerInfo;
        private FlightInfo flightInfo;
    }
    @Data
    public static class BasicInfo {
        @Size(max = 14)
        private String policyNum;
        @Size(max = 10)
        private String effectDateStart;
        @Size(max = 10)
        private String effectDateEnd;
        @Size(max = 10)
        private String aEffectDateStart;
        @Size(max = 10)
        private String effectDate;
        @Digits(integer = 15, fraction = 2)
        private Double premium;
        @Size(max = 1)
        private String policyStatus;
        @Digits(integer = 15, fraction = 2)
        private Double voltPremium;
        @Digits(integer = 15, fraction = 2)
        private Double compPremium;
        private Boolean isEIP;
        private String fubonLifeAgree;
        private String cgrCode;
        private String clsCode;
        private String pnCode;
        private Double totalPremium100;
        private Double ourshr;
        private String ptcptNo;
    }
    @Data
    public static class InsuredInfo {
        @Size(max = 200)
        private String insuredName;
        @Size(max = 11)
        private String insuredId;
        private Calendar insuredBirthDate;
        @Size(max = 200)
        private String insuredAddr;
        @Size(max = 30)
        private String insuredTel1;
        @Size(max = 30)
        private String insuredTel2;
        @Size(max = 300)
        private String insuredEmail;
        @Size(max = 20)
        private String insuredPhone;
        @Size(max = 120)
        private String insuredCompany;
        @Size(max = 120)
        private String insuredContent;
        @Size(max = 1)
        private String insuredCrcGrop;
        private String insuredPlanCode;
        private String insuredQualification;
        @Size(max = 200)
        private String insuredRepresentative;
        @Size(max = 10)
        private String insuredRepresentativeId;
    }
    @Data
    public static class InsuredList {
        @Size(max = 200)
        private String insuredName;
        @Size(max = 11)
        private String insuredId;
        private Calendar insuredBirthDate;
        @Size(max = 200)
        private String insuredAddr;
        @Size(max = 30)
        private String insuredTel1;
        @Size(max = 30)
        private String insuredTel2;
        @Size(max = 300)
        private String insuredEmail;
        @Size(max = 20)
        private String insuredPhone;
        @Size(max = 120)
        private String insuredCompany;
        @Size(max = 120)
        private String insuredContent;
        @Size(max = 1)
        private String insuredCrcGrop;
        private String insuredPlanCode;
        private String insuredQualification;
        @Size(max = 200)
        private String insuredRepresentative;
        @Size(max = 10)
        private String insuredRepresentativeId;
    }
    @Data
    public static class ProposerInfo {
        @Size(max = 60)
        private String proposerName;
        @Size(max = 11)
        private String proposerId;
        private Calendar proposerBirthDate;
        @Size(max = 200)
        private String proposerAddr;
        @Size(max = 30)
        private String proposerTel1;
        @Size(max = 30)
        private String proposerTel2;
        @Size(max = 300)
        private String proposerEmail;
        @Size(max = 20)
        private String proposerPhone;
        private Long totalNum;
        @Size(max = 120)
        private String relation;
        @Size(max = 1)
        private String payer;
        @Size(max = 200)
        private String payMode;
        private String payMethod;
        private String cardNo;
        private String cardExpDate;
        @Size(max = 200)
        private String proposerRepresentative;
        @Size(max = 10)
        private String proposerRepresentativeId;
    }
    @Data
    public static class FlightInfo {
        @Size(max = 10)
        private Integer filghtSeq;
        @Size(max = 10)
        private String filghtCode;
        @Size(max = 200)
        private String filghtNo;
        private Calendar filghtDate;
        @Size(max = 10)
        private String filghtCity;
    }
    @Data
    public static class InsuranceSubject {
        @Size(max = 11)
        private String plateNo;
        @Size(max = 2)
        private String plateCode;
        @Size(max = 10)
        private String lisnDate;
        @Size(max = 6)
        private String leaveFactoryYearDate;
        @Digits(integer = 8, fraction = 2)
        private Double displa;
        @Digits(integer = 5, fraction = 1)
        @Size(max = 20)
        private String engineNo;
        @Size(max = 8)
        private String vehTramak;
        @Size(max = 200)
        private String subjectAddr;
        @Size(max = 3)
        private String buildYear;
        @Size(max = 10)
        private String buildOccp;
        @Size(max = 5)
        private String buildRoof;
        @Size(max = 5)
        private String buildMaterial;
        @Size(max = 2)
        private String buildConstr;
        @Size(max = 1)
        private String buildSection;
        @Digits(integer = 10, fraction = 0)
        private Double buildHeight;
        @Digits(integer = 10, fraction = 3)
        private Double buildSpace;
        @Size(max = 100)
        private List<String> mortgagee;
        @Size(max = 1)
        private String buildFloor;
        @Size(max = 1)
        private String buildAddFeeFlag;
        private Long totalNum;
        @Size(max = 3)
        private String travelCity1;
        @Size(max = 3)
        private String travelCity2;
        @Size(max = 3)
        private String travelCity3;
        @Size(max = 3)
        private String travelCity4;
        @Size(max = 100)
        private String mountainLocation;
        @Size(max = 20)
        private String activityLoaction;
    }
    @Data
    public static class EtpInsuranceSubject {
        private String content;
        private String desc;
    }
    @Data
    public static class EtpInsuranceSubjectDetail{
        private Integer seq;
        private List<String> values;
    }
    @Data
    public static class InsuranceItem{
        @Size(max = 500)
        private String bnfCode;
        private List<PitEb0List> eb0Lists;
        @Digits(integer = 15, fraction = 2)
        private Double finalPrm;
        @Digits(integer = 15, fraction = 2)
        private Double pex;
        private String title;
        private Collection<String> values;
    }
    @Data
    public static class PitEb0List{
        private String eb0TsiDesc;
        private String eb0TsiValue;
        private String eb0TsiUnit;
    }
    @Data
    public static class InsuranceList{
        @Size(max = 10)
        private String bnfCode;
        @Size(max = 11)
        private String name;
        @Size(max = 200)
        private String uid;
        @Size(max = 10)
        private String birthDate;
        @Size(max = 20)
        private String title;
        @Size(max = 20)
        private String relation;
        @Size(max = 120)
        private String beneficiary;
        @Size(max = 100)
        private String petType;

        private String petName;
        @Size(max = 1)
        private String petSex;
        @Size(max = 10)
        private Integer petSeq;

        private Calendar petBirthDate;
        @Size(max = 1)
        private Integer petAge;
    }
    @Data
    public static class PolicyDeliveryRecord{
        @Size(max = 100)
        private String prmDocName;
        @Size(max = 1)
        private String prmFormat;
        @Size(max = 1)
        private String prmType;
        @Size(max = 1)
        private String prmSendType;
        @Size(max = 1)
        private String prmPrintStatus;

        private Date prmPrintDate;
    }
    @Data
    public static class UnpaidRecord{
        @Size(max = 3)
        private String insType;
        @Size(max = 16)
        private String policyNum;
        @Size(max = 16)
        private String quoteNum;
        @Size(max = 160)
        private String proposer;
        @Size(max = 160)
        private String insured;
        @Size(max = 11)
        private String platno;

        private String effdate;

        @DecimalMax(value = "11")
        private BigDecimal premium;
    }
    @Data
    public static class PaidRecord{
        @Size(max = 3)
        private String insType;
        @Size(max = 16)
        private String policyNum;
        @Size(max = 16)
        private String quoteNum;
        @Size(max = 160)
        private String proposer;
        @Size(max = 160)
        private String insured;
        @Size(max = 11)
        private String platno;

        private String effdate;
        @DecimalMax(value = "11") @DecimalMin(value = "2")
        private BigDecimal payamt;

        private Date pcllDate;
        @Size(max = 30)
        private String payKind;
    }
    @Data
    public static class ClaimRecord{
        private String insType;
        private Date acdate;
        private String clamNo;
        private String polyNo;
        private String insuredName;
        private String accper;
        private String clamStatus;
        private String prcdeptNm;
        private String prcempNm;
        private String phone;
    }
    @Data
    public static class ConservationRecord{

    }

}
