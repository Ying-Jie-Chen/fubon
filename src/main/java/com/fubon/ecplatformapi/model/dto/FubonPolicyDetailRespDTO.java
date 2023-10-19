package com.fubon.ecplatformapi.model.dto;

import lombok.Data;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * 富邦API - 取得保單資訊
 * 車個險用：EcAppInsure 企業險用：EpAppInsureEtp
 *
 */
@Data
public class FubonPolicyDetailRespDTO {

    private EcAppInsure ecAppInsure;
    private EcAppInsureEtp ecAppInsureEtp;

    @Data
    public static class EcAppInsure {
        private String voltPolicyNum;
        private String compPolicyNum;
        private Double voltPremium;
        private Double compPremium;
        private Double totalPremium;
        private String polSt;
        private SecEcAppWsBean secEcAppWsBean;
        private EecEcAppWsBean eecEcAppWsBean;
        private RmaEcAppWsBean rmalEcAppWsBean;
        private EcoEcAppWsBeans ecoEcAppWsBean; // 原本是 List
        private RmaEcAppWsBean rmaAEcAppWsBean;
        private CrdEcAppWsBean crdEcAppWsBean;
        private Collection<PitEcAppWsBean> pitEcAppWsBeans;
        private Collection<PitNecAppWsBeans> pitNEcAppWsBeans;
        private AscEcAppWsBean ascEcAppWsBean;
        private MohEcAppWsBean mohEcAppWsBean;
        private RskEcAppWsBean rskEcAppWsBean;
        private BudEcAppWsBean budEcAppWsBean;
        private List<RmaMEcAppWsBean> rmaMEcAPpWsBeans;
        private List<MidEcAPpWsBean> midEcAppWsBeans;
        private List<FliEcAppWsBeans> fliEcAppWsBeans;
        private List<BenEcAppWsBean> benEcAppWsBeans;

    }
    @Data
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
        private Collection<PitEcAppEtpWsBean> pitEcAppWsBean;

    }
    @Data
    public static class SecEcAppWsBean {
        private String secFormatid;
        private String secEffdate;
        private String secExpdate;
        private String secAeffdate;
        private String secAexpdate;
        private Boolean secEip;
        private String secFubonlifeAgree;
        private String secPayMode;
        private String secPaymthd;
        private Long secTotalnum;
        private String secCty1;
        private String secCty2;
        private String secCty3;
        private String secCty4;
    }
    @Data
    public static class EecEcAppWsBean {
        private String eecMountain;
        private String eecActivity;
    }
    @Data
    public static class RmaEcAppWsBean {
        private String rmaType;
        private String rmaCliname;
        private String rmaUid;
        private Calendar rmaPBirth;
        private String rmaNat;
        private String rmaCAddrCde;
        private String rmaAddr;
        private String rmaTel1;
        private String rmaTel2;
        private String rmaEmail;
        private String rmaMobTel;
        private String rmaRepresentative;
        private String rmaRepresentativeId;
        private String rmaRela;
        private String rmaRelaRemark;
        private String rmaQualification;
    }
    @Data
    public static class EcoEcAppWsBeans {
        private Integer ecoSeq;
        private String ecoUid;
        private String ecoNat;
        private String ecoCname;
        private Calendar ecoBirdate;
        private String ecoEmail;
        private String ecoMob;
        private String ecoCompnm;
        private String ecoContent;
        private String ecoCrcGrp;
        private String ecoPtcptno;
        private String ecoReltn;
    }
    @Data
    public static class MohEcAppWsBean {
        private String mohPlatno;
        private String mohMotCode;
        private String mohLisndate;
        private String mohBuidyr;
        private Double mohDispla;
        private Double mohPeopno1;
        private String mohEngnno;
        private String mohVehTramak;
    }
    @Data
    public static class CrdEcAppWsBean {
        private String crdCardno;
        private String crdExpdate;
    }
    @Data
    public static class PitEcAppWsBean {
        private String pitBnfCode;
        private String pitEb0Name;
        private String pitType;
        private Double pitFinalprm;
        private Double pitPex;
        private String pitPexUnit;
        private List<PitEb0List> pitEb0Lists;
    }
    @Data
    public static class PitEb0List {
        private String eb0TsiDesc;
        private String eb0TsiValue;
        private String eb0TsiUnit;
    }
    @Data
    public static class PitNecAppWsBeans {
        private String pitBnfCode;
        private String pitNUid;
        private String pitNIsrName;
        private String pitNBirth;
        private String pitNAppRelation;
        private String pitNBeneficiary;
    }
    @Data
    public static class RskEcAppWsBean {
        private String rskLocation;
        private String rskSection;
    }
    @Data
    public static class BudEcAppWsBean {
        private String budBuildyear;
        private String budOccp;
        private String budRoof;
        private String budMaterial;
        private String budConstr;
        private Double budHeight;
        private Double budSpace;
        private String budSpaceUnit;
        private String budFlrType;
        private String budAddfeeMark;
    }
    @Data
    public static class RmaMEcAppWsBean {
        private String rmaCliname;
    }
    @Data
    public static class AscEcAppWsBean {
        private String ascAdmin;
        private String ascCrzSale;
        private String ascIscXref;
    }
    @Data
    public static class MidEcAPpWsBean {
        private String midContent;
        private String midName;
        private String midSex;
        private Integer midSeq;
        private Calendar midBirdate;
        private Integer midAge;
    }
    @Data
    public static class FliEcAppWsBeans {
        private Integer fliRskSeq;
        private String fliFlightCode;
        private String fliFlightType;
        private String fliFlightNo;
        private Calendar fliFlightDate;
        private String fliFlightCty;
    }
    @Data
    private static class PitEcAppEtpWsBean {
        private Integer pitRskSeq;
        private String pitRskType;
        private Collection<String> values;
    }
    @Data
    private static class RskEcAppEtpWsBean {
        private String content;
        private String desc;
    }
    @Data
    private static class RskDEcAppEtpWsBean {
        private Integer seq;
        private Collection<String> values;
    }
    @Data
    private static class BenEcAppWsBean {
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
}
