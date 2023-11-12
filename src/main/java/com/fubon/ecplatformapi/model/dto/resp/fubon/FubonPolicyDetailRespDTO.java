package com.fubon.ecplatformapi.model.dto.resp.fubon;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * 富邦API - 取得保單資訊
 * 車個險用：EcAppInsure 企業險用：EpAppInsureEtp
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FubonPolicyDetailRespDTO {

    private EcAppInsure ecAppInsure;
    private EcAppInsureEtp ecAppInsureEtp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EcAppInsure {
        @Size(max = 14)
        private String voltPolicyNum;
        @Size(max = 14)
        private String compPolicyNum;
        @Digits(integer = 15, fraction = 2)
        private Double voltPremium;
        @Digits(integer = 15, fraction = 2)
        private Double compPremium;
        @Digits(integer = 15, fraction = 2)
        private Double totalPremium;
        @Size(max = 1)
        private String polSt;
        private SecEcAppWsBean secEcAppWsBean;
        private EecEcAppWsBean eecEcAppWsBean;
        private RmaEcAppWsBean rmalEcAppWsBean;
        private List<EcoEcAppWsBeans> ecoEcAppWsBean;
        private RmaEcAppWsBean rmaAEcAppWsBean;
        private CrdEcAppWsBean crdEcAppWsBean;
        private Collection<PitEcAppWsBean> pitEcAppWsBeans;
        private Collection<PitNecAppWsBeans> pitNEcAppWsBeans;
        private AscEcAppWsBean ascEcAppWsBean;
        private MohEcAppWsBean mohEcAppWsBean;
        private RskEcAppWsBean rskEcAppWsBean;
        private BudEcAppWsBean budEcAppWsBean;
        private List<RmaMEcAppWsBean> rmaMEcAPpWsBeans;
        private List<MidEcAppWsBean> midEcAppWsBeans;
        private List<FliEcAppWsBeans> fliEcAppWsBeans;
        private List<BenEcAppWsBean> benEcAppWsBeans;
        private List<SbcEcAppWsBean> sbcEcAppWsBeans;
    }
    @Data
    @Builder
    public static class EcAppInsureEtp {
        private String policyNum;
        private String clsCode;
        private String cgrCode;
        private String plnCode;
        private Double totalPremium;
        private Double totalPremium100;
        private Double ourshr;
        private SecEcAppWsBean secEcAppWsBean;
        private RmaEcAppWsBean rmaIEcAppWsBean;
        private RmaEcAppWsBean rmaAEcAppWsBean;
        private Collection<RskEcAppEtpWsBean> rskEcAppWsBeans;
        private Collection<RskDEcAppEtpWsBean> rskDEcAppWsBeans;
        private Collection<String> pitColumnNames;
        private Collection<PitEcAppEtpWsBean> pitEcAppEtpWsBeans;
        private Collection<EcoEcAppWsBean> ecoEcAppWsBeans;


    }
    @Data
    @Builder
    public static class SecEcAppWsBean {
        @Size(max = 14)
        private String secFormatid;
        @Size(max = 10)
        private String secEffdate;
        @Size(max = 10)
        private String secExpdate;
        @Size(max = 10)
        private String secAeffdate;
        @Size(max = 10)
        private String secAexpdate;
        @Size(max = 1)
        private Boolean secEip;
        @Size(max = 1)
        private String secFubonlifeAgree;
        @Size(max = 1)
        private String secPayMode;
        @Size(max = 1)
        private String secPaymthd;
        @Size(max = 100)
        private String secMtg;
        @Size(max = 100)
        private String secMtg2;
        private Long secTotalnum;
        @Size(max = 3)
        private String secCty1;
        @Size(max = 3)
        private String secCty2;
        @Size(max = 3)
        private String secCty3;
        @Size(max = 3)
        private String secCty4;
    }
    @Data
    @Builder
    public static class EecEcAppWsBean {
        @Size(max = 100)
        private String eecMountain;
        @Size(max = 20)
        private String eecActivity;
    }
    @Data
    @Builder
    public static class RmaEcAppWsBean {
        @Size(max = 14)
        private String rmaType;
        @Size(max = 200)
        private String rmaCliname;
        @Size(max = 3)
        private String rmaUid;
        @Size(max = 10)
        private Calendar rmaPBirth;
        @Size(max = 10)
        private String rmaNat;
        @Size(max = 3)
        private String rmaCAddrCde;
        @Size(max = 200)
        private String rmaAddr;
        @Size(max = 30)
        private String rmaTel1;
        @Size(max = 30)
        private String rmaTel2;
        @Size(max = 160)
        private String rmaEmail;
        @Size(max = 30)
        private String rmaMobTel;
        @Size(max = 200)
        private String rmaRepresentative;
        @Size(max = 10)
        private String rmaRepresentativeId;
        @Size(max = 2)
        private String rmaRela;
        @Size(max = 40)
        private String rmaRelaRemark;
        @Size(max = 1)
        private String rmaQualification;
    }
    @Data
    @Builder
    public static class EcoEcAppWsBeans {
        @Size(max = 10)
        private Integer ecoSeq;
        @Size(max = 11)
        private String ecoUid;
        @Size(max = 1)
        private String ecoNat;
        @Size(max = 60)
        private String ecoCname;

        private Calendar ecoBirdate;
        @Size(max = 300)
        private String ecoEmail;
        @Size(max = 20)
        private String ecoMob;
        @Size(max = 120)
        private String ecoCompnm;
        @Size(max = 120)
        private String ecoContent;
        @Size(max = 1)
        private String ecoCrcGrp;
        @Size(max = 1)
        private String ecoPtcptno;
        @Size(max = 2)
        private String ecoReltn;
        @Size(max = 5)
        private Integer ecoAge;
        @Size(max = 11)
        private String ecoPlnCode;
    }
    @Data
    @Builder
    public static class MohEcAppWsBean {
        @Size(max = 11)
        private String mohPlatno;
        @Size(max = 2)
        private String mohMotCode;
        @Size(max = 10)
        private String mohLisndate;
        @Size(max = 6)
        private String mohBuidyr;
        @Digits(integer = 8, fraction = 2)
        private Double mohDispla;
        @Digits(integer = 5, fraction = 1)
        private Double mohPeopno1;
        @Size(max = 20)
        private String mohEngnno;
        @Size(max = 8)
        private String mohVehTramak;
        @Size(max = 10)
        private String mohPrmCode;
        @Size(max = 11)
        private String mohPlnCode;
    }
    @Data
    @Builder
    public static class CrdEcAppWsBean {
        @Size(max = 10)
        private String crdPrsnid;
        @Size(max = 200)
        private String crdPrsname;
        @Size(max = 1)
        private String crdPrstype;
        @Size(max = 16)
        private String crdCardno;
        @Size(max = 6)
        private String crdExpdate;
    }
    @Data
    @Builder
    public static class PitEcAppWsBean {
        private Integer pitSeq;
        @Size(max = 10)
        private String pitBnfCode;
        @Size(max = 500)
        private String pitEb0Name;
        @Size(max = 1)
        private String pitType;
        @Digits(integer = 15, fraction = 2)
        private Double pitFinalprm;
        @Digits(integer = 15, fraction = 2)
        private Double pitPex;
        @Size(max = 50)
        private String pitPexUnit;
        @JsonProperty("pitEb0Lists")
        private List<PitEb0List> pitEb0Lists;
    }
    @Data
    @Builder
    public static class PitEb0List {
        private String eb0TsiDesc;
        private String eb0TsiValue;
        private String eb0TsiUnit;
    }
    @Data
    @Builder
    public static class PitNecAppWsBeans {
        @Size(max = 10)
        private String pitBnfCode;
        @Size(max = 11)
        private String pitNUid;
        @Size(max = 200)
        private String pitNIsrName;
        @Size(max = 10)
        private String pitNBirth;
        @Size(max = 20)
        private String pitNAppRelation;
        @Size(max = 120)
        private String pitNBeneficiary;

        private String pitNTitle;

    }
    @Data
    @Builder
    public static class RskEcAppWsBean {
        @Size(max = 200)
        private String rskLocation;
        @Size(max = 1)
        private String rskSection;
    }
    @Data
    @Builder
    public static class BudEcAppWsBean {
        @Size(max = 3)
        private String budBuildyear;
        @Size(max = 10)
        private String budOccp;
        @Size(max = 5)
        private String budRoof;
        @Size(max = 5)
        private String budMaterial;
        @Size(max = 2)
        private String budConstr;
        @Digits(integer = 10, fraction = 0)
        private Double budHeight;
        @Digits(integer = 10, fraction = 3)
        private Double budSpace;
        @Size(max = 2)
        private String budSpaceUnit;
        @Size(max = 2)
        private String budFlrType;
        @Size(max = 1)
        private String budAddfeeMark;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RmaMEcAppWsBean {
        @Size(max = 100)
        private String rmaCliname;
    }
    @Data
    @Builder
    public static class AscEcAppWsBean {
        @Size(max = 10)
        private String ascAdmin;
        @Size(max = 10)
        private String ascCrzSale;
        @Size(max = 10)
        private String ascIscXref;
    }
    @Data
    @Builder
    public static class MidEcAppWsBean {
        @Size(max = 100)
        private String midContent;
        private String midName;
        @Size(max = 1)
        private String midSex;
        @Size(max = 10)
        private Integer midSeq;
        private Calendar midBirdate;
        @Size(max = 10)
        private Integer midAge;
    }
    @Data
    @Builder
    public static class FliEcAppWsBeans {
        @Size(max = 10)
        private Integer fliRskSeq;
        @Size(max = 100)
        private String fliFlightCode;
        @Size(max = 10)
        private String fliFlightType;
        @Size(max = 100)
        private String fliFlightNo;

        private Calendar fliFlightDate;
        @Size(max = 10)
        private String fliFlightCty;
    }
    @Data
    @Builder
    public static class PitEcAppEtpWsBean {
        private Integer pitRskSeq;
        private String pitRskType;
        private Collection<String> values;
    }
    @Data
    @Builder
    public static class RskEcAppEtpWsBean {
        private String content;
        private String desc;
    }
    @Data
    @Builder
    public static class RskDEcAppEtpWsBean {
        private Integer seq;
        private Collection<String> values;
    }

    @Data
    @Builder
    public static class BenEcAppWsBean {
        private String benRsktype;
        private Integer benRskSeq;
        private Integer benPitseq;
        private String benType;
        private Integer benSeq;
        private String benCname;
        private String benUid;
        private String benRelation;
        private String benTel;
        private String benCAddrCde;
        private String benAddr;
        private String benMemo;
    }
    @Data
    @Builder
    public static class SbcEcAppWsBean {
        @Size(max = 20)
        private String sbcCode;
        @Size(max = 1)
        private String sbcApply;
        @Size(max = 20)
        private String sbcMohParam1;
        @Digits(integer = 17, fraction = 4)
        private Double sbcMohParam2;
        @Digits(integer = 17, fraction = 4)
        private Double sbcMohParam3;
        @Digits(integer = 17, fraction = 4)
        private Double sbcMohParam4;
        @Digits(integer = 17, fraction = 4)
        private Double sbcMohParam5;
    }

    @Data
    @Builder
    private static class  EcoEcAppWsBean {
        private String ecoSeq;
        private String ecoGprId;
        private String ecoCreGrp;
        private String ecoUid;
        private String ecoCname;
        private String ecoNation;
        private String ecoContent;
        private String ecoTitle;
        private String ecoTsi;
        private String ecoTsiCcy;
        private String prm;
        private String ecoPeriod;
        private String ecoGprIdDesc;
    }

}
