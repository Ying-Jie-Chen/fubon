package com.fubon.ecplatformapi.model.dto.resp.fubon;

import lombok.Data;

import java.util.Calendar;
import java.util.List;
@Data
public class QueryPolicyListRespDTO {

    private List<PolicyResult> policyResults;
    @Data
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
