package com.fubon.ecplatformapi.model.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyListResultVO {

    private String insType;

    private String policyNum;

    private Integer premiums;

    private String insuredName;

    private String plate;

    private Date effectDate;

    private Date expireDate;
}

