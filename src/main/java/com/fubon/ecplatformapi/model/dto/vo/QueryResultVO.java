package com.fubon.ecplatformapi.model.dto.vo;

import com.fubon.ecplatformapi.model.entity.QueryReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResultVO {

        private String insType;
        private String policyNum;
        private Integer premiums;
        private String insuredName;
        private String plate;
        private Date effectDate;
        private Date expireDate;
}

