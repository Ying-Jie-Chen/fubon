package com.fubon.ecplatformapi.model.dto.resp.fubon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPolicyListRespDTO {

    @JsonProperty("policyResults")
    private List<PolicyResult> policyResults;
    @Data
    @Builder
   public static class PolicyResult{
       private String clsGrp;
       private String module;
       private String polFormatid;
       private String rmaClinameI;
       private String rmaUidI;
       private String mohPlatno;
       private Calendar secEffdate;
       private Calendar secExpdate;
       private String ascIscXref;
       private Integer unPaidPrm;
   }
}
