package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class FbQueryRespDTO {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<PolicyResult> policyResults;

    @JsonCreator
    public FbQueryRespDTO(@JsonProperty("policyResults") List<PolicyResult> policyResults) {
        this.policyResults = policyResults;
    }

    @Data
    @Builder
    public static class PolicyResult {
        @JsonProperty("clsGrp")
        private String clsGrp;

        @JsonProperty("module")
        private String module;

        @JsonProperty("polFormatid")
        private String polFormatid;

        @JsonProperty("rmaClinameI")
        private String rmaClinameI;

        @JsonProperty("rmaUidI")
        private String rmaUidI;

        @JsonProperty("mohPlatno")
        private String mohPlatno;

        @JsonProperty("secEffdate")
        private Date secEffdate;

        @JsonProperty("secExpdate")
        private Date secExpdate;

        @JsonProperty("ascIscXref")
        private String ascIscXref;

        @JsonProperty("unPaidPrm")
        private Integer unPaidPrm;

    }

}
