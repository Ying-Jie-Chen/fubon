package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class FbQueryRespDTO {

//    private List<PolicyResult> policyResults;
//
//    @Data
//    @Builder
//    public static class PolicyResult {
        @JsonProperty("insType")
        private String clsGrp;
        private String module;
        @JsonProperty("policyNum")
        private String polFormatid;
        @JsonProperty("insuredName")
        private String rmaClinameI;
        private String rmaUidI;
        @JsonProperty("plate")
        private String mohPlatno;
        @JsonProperty("effectDate")
        private Date secEffdate;
        @JsonProperty("expireDate")
        private Date secExpdate;
        private String ascIscXref;
        @JsonProperty("premiums")
        private Integer unPaidPrm;

    //}

}
