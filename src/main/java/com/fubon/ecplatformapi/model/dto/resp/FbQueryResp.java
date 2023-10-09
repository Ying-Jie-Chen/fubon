package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fubon.ecplatformapi.FbQueryRespDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(using = FbQueryRespDeserializer.class)
public class FbQueryResp {
    private List<PolicyResult> policyResults;

    @Data
    @AllArgsConstructor
    @Builder
    public static class PolicyResult {

        private String clsGrp;
        private String module;
        private String polFormatid;
        private String rmaClinameI;
        private String rmaUidI;
        private String mohPlatno;
        private Calendar  secEffdate;
        private Calendar secExpdate;
        private String ascIscXref;
        private Integer unPaidPrm;

        public PolicyResult() {
        }
    }

    public FbQueryResp() {
    }


}
