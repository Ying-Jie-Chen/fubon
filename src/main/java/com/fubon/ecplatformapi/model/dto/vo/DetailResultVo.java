package com.fubon.ecplatformapi.model.dto.vo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Data
@Builder
public class DetailResultVo {

    private InsuranceInfo insuranceInfo; // 保單基本資料：富邦API - 取得保單資訊
    private InsuranceSubject insuranceSubject; // 保險標的：富邦API - 取得保單資訊
    private List<EtpInsuranceSubject> etpInsuranceSubject; // 企業險保險標的：富邦API - 取得保單資訊
    private List<EtpInsuranceSubjectDetail> etpInsuranceSubjectDetail; // 企業險保險標的明細：富邦API - 取得保單資訊
    private List<InsuranceItem> insuranceItem; // 保險項目：富邦API - 取得保單資訊
    private List<InsuranceList> insuranceList; // 險種名冊：富邦API - 取得保單資訊
    private List<InsuranceOtherList> insuranceOtherList; // 其他險種名冊 個人傷害險的寵物險
    private List<PolicyDeliveryRecord> policyDeliveryRecord; // 保單寄送記錄：富邦API - 保單寄送紀錄查詢
    private List<UnpaidRecord> unpaidRecord; // 未繳保費
    private List<PaidRecord> paidRecord; // 繳費紀錄：繳費記錄/未繳費記錄 未繳費記錄查詢條件：NFNV02.POLYNO ＝保單號碼 繳費記錄查詢條件：NFNV03.POLYNO ＝保單號碼
    private List<ClaimRecord> claimRecord; // 理賠記錄：富邦API - 理賠紀錄查詢
    private List<ConservationRecord> conservationRecord; // 保全紀錄：富邦API - 保全紀錄查詢

    @Data
    @Builder
    public static class InsuranceInfo {
        private BasicInfo basicInfo;
        private InsuredInfo insuredInfo;
        private List<InsuredList> insuredList;
        private ProposerInfo proposerInfo;
        private List<FlightInfo> flightInfo;
    }

    @Data
    @Builder
    @AllArgsConstructor
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
        private String relationPeople;
        private String relationPeople2;

    }

    @Data
    @Builder
    @AllArgsConstructor
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
    @Builder
    @AllArgsConstructor
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
        @Size(max = 20)
        private String title;
        @Size(max = 120)
        private String beneficiary;
        @Size(max = 5)
        private Integer age;
    }

    @Data
    @Builder
    @AllArgsConstructor
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
    @Builder
    @AllArgsConstructor
    public static class FlightInfo {
        @Size(max = 10)
        private Integer flightSeq;
        @Size(max = 10)
        private String flightCode;

        @Size(max = 200)
        private String flightNo;

        private Calendar flightDate;
        @Size(max = 10)
        private String flightCity;
    }

    @Data
    @Builder
    @AllArgsConstructor
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
        private Double peopno1;
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
        private String buildSpace;
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
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EtpInsuranceSubject {
        private String content;
        private String desc;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class EtpInsuranceSubjectDetail{
        private Integer seq;
        private List<String> values;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class InsuranceItem{
        @Size(max = 500)
        private String bnfCode;
        private List<PitEb0List> eb0Lists;
        @Digits(integer = 15, fraction = 2)
        private Double finalPrm;
        @Digits(integer = 15, fraction = 2)
        private Double pex;
        private Integer seq;
        private String title;
        private List<List<String>> values;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PitEb0List{
        private String eb0TsiDesc;
        private String eb0TsiValue;
        private String eb0TsiUnit;
    }

    @Data
    @Builder
    @AllArgsConstructor
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
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class InsuranceOtherList {
        @Size(max = 100)
        private String petType;
        private String petName;
        @Size(max = 1)
        private String petSex;
        @Size(max = 10)
        private Integer petSeq;
        private Calendar petBirthDate;
        @Size(max = 10)
        private Integer petAge;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class PolicyDeliveryRecord{
        @Size(max = 30)
        private String prnDoc;
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
    @Builder
    @AllArgsConstructor
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
    @Builder
    @AllArgsConstructor
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

        @DecimalMax(value = "13")
        @DecimalMin(value = "2")
        private BigDecimal payamt;

        private Date pcllDate;

        @Size(max = 30)
        private String payKind;
    }

    @Data
    @Builder
    @AllArgsConstructor
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
    @Builder
    @AllArgsConstructor
    public static class ConservationRecord{
        private String proposerName;
        private String insuredName;
        private String policyName;
        private Date createDate;
        private Date modifyDate;
        private String wrpStatus;
        private Date closeDate;

    }

}
