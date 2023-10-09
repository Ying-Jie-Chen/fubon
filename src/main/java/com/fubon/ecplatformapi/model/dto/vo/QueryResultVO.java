package com.fubon.ecplatformapi.model.dto.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QueryResultVO {
        private List<QueryResult> data;

        @Data
        public static class QueryResult {
                private String insType;
                private String policyNum;
                private Integer premiums;
                private String insuredName;
                private String plate;
                private Date effectDate;
                private Date expireDate;
        }
}

