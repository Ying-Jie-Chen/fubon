package com.fubon.ecplatformapi.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FbQueryRespDTO {

    private List<PolicyResult> policyResults;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
