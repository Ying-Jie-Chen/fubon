package com.fubon.ecplatformapi.model.dto.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MyPolicyListVO {
    private String insType;
    private String policyNum;
    private Integer premiums;
    private String insuredName;
    private String plate;
    private Date effectDate;
    private Date  expireDate;
}
