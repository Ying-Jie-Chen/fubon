package com.fubon.ecplatformapi.model.dto.resp.fubon;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
public class FubonChkEnrDataRespDTO {
    private String responseCode;
    private String responseErrorCode;
    private String responseMsg;
    private Collection<RecEcAppWsBean> recEcAppWsBeans;
    @Data
    @Builder
    public static class RecEcAppWsBean {
        private String formatid;
        private String endst;
        private String rmaACliname;
        private String rmalCliname;
        private Date secCdate;
        private Date secAdate;
        private String secWrpsts;
        private Date closeDate;
    }
}
