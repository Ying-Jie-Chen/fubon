package com.fubon.ecplatformapi.model.dto.resp;

import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class FbDetailResp {

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
        //private EecEcAppWsBean eecEcAppWsBean;
        private RmaEcAppWsBean rmalEcAppWsBean;
        private EcoEcAppWsBeans ecoEcAppWsBean; // 原本是 List
        private RmaEcAppWsBean rmaAEcAppWsBean;

    }
    @Data
    public static class EcoEcAppWsBeans {
        private Integer ecoSeq;
        private String ecoUid;
        private String ecoNa;
        private String ecoCname;
        private Calendar ecoBirdate;
        private String ecoEmail;
        private String ecoMob;
        private String ecoCompnm;
        private String ecoContent;
        private String ecoCrcGrp;
        public String ecoPtcptno;
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
    public static class EcAppInsureEtp {
        private String policyNum;
        private String clsCode;
        private String cgrCode;
        private String plnCode;
        private Double totalPremium;
        private Double totalPremium100;
        private Double ourshr;
        private SecEcAppWsBean secEcAppWsBean;

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


}
