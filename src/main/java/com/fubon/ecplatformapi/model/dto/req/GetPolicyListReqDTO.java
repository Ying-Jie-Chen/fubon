package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class GetPolicyListReqDTO {
    private String clsGrp;
    private String module;
    private String secFormatid;
    private String rmaClinameI;
    private String rmaUidI;
    private String rmaClinameA;
    private String rmaUidA;
    private String mohPlatno;
    private String secTradeNo;
    private String ascAdmin;
    private String ascIscXref;
    private String fbId;
    private String dateType;
    private Calendar dateFr;
    private Calendar dateTo;
    private String sourcePage;
}
