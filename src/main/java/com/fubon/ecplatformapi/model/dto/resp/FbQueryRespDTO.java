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
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class FbQueryRespDTO {

    //@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<PolicyResult> policyResults;

    public FbQueryRespDTO(List<PolicyResult> policyResults) {
        this.policyResults = policyResults;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class PolicyResult {
        public PolicyResult() { }

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
