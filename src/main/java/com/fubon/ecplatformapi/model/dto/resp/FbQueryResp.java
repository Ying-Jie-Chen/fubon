package com.fubon.ecplatformapi.model.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class FbQueryResp {
    private List<PolicyResult> policyResults;

    @Data
    @Builder
    public static class PolicyResult {

        private String clsGrp;
        private String module;
        private String polFormatid;
        private String rmaClinameI;
        private String rmaUidI;
        private String mohPlatno;
        private Date secEffdate;
        private Date secExpdate;
        private String ascIscXref;
        private Integer unPaidPrm;

    }

}
