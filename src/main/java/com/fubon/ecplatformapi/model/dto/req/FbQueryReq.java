package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Builder
public class FbQueryReq {
    private String clsGrp;
    private String module;
    private String seeFormatid;
    private String rmaClinamel;
    private String rmaUidI;
    private String rmaClinameA;
    private String rmaUidA;
    private String mohPlatno;
    private String secTradeNo;
    private String ascAdmin;
    private String ascIscXref;
    private String fbId;
    private String dataType;
    private Date dateFr;
    private Date dateTo;
    private String sourcePage;
}
