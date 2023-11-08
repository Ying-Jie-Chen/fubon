package com.fubon.ecplatformapi.model.dto.resp.fubon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class FubonClmSalesRespDTO {
    private Integer result;
    private String message;
    private Integer total;
    private Integer count;

    @JsonProperty("content")
    private Content content;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {

        @JsonProperty("claimInfo")
        private List<ClaimInfo> claimInfo;
        @Data
        @Builder
        public static class ClaimInfo {
            private Integer no;
            private String queryPlan;
            private Date acdate;
            private String clamno;
            private String queryPolyNo;
            private String icname;
            private String accper;
            private String clamsts;
            private String prcdeptnm;
            private String prcempnm;
            private String phone;

            public Date getAcdtda() {
                return acdate;
            }
        }
    }
}
