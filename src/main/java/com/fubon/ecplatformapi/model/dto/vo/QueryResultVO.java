package com.fubon.ecplatformapi.model.dto.vo;

import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QueryResultVO {
        private String insType;
        private String policyNum;
        private Integer premiums;
        private String  insuredName;
        private String plate;
        private Date effectDate;
        private Date expireDate;

}
