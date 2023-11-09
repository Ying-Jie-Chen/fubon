package com.fubon.ecplatformapi.model.dto.resp.fubon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FubonChkEnrDataRespDTO {
    private String responseCode;
    private String responseErrorCode;
    private String responseMsg;
    @JsonProperty("recEcAppWsBeans")
    private Collection<RecEcAppWsBean> recEcAppWsBeans;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
