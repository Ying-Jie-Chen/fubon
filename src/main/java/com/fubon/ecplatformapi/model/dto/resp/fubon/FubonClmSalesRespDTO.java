package com.fubon.ecplatformapi.model.dto.resp.fubon;

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
    private Content content;
    @Data
    @Builder
    public static class Content {
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
