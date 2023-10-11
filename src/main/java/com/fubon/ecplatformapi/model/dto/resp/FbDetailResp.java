package com.fubon.ecplatformapi.model.dto.resp;

import lombok.Data;

@Data
public class FbDetailResp {

    private EcAppInsure ecAppInsure;
    private EcAppInsureEtp ecAppInsureEtp;

    @Data
    public static class EcAppInsure {
        private Double totalPremium;
        private SecEcAppWsBean secEcAppWsBean;


    }

    @Data
    public static class SecEcAppWsBean {
        private String secEffdate;
        private String secExpdate;
        private String secAeffdate;
        private String secAexpdate;
        private String secEip;
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
    }


}
