package com.fubon.ecplatformapi.model.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ListResultVO {
        private String insType;
        private String policyNum;
        private Integer premiums;
        private String insuredName;
        private String plate;
        private Date effectDate;
        private Date expireDate;
}

