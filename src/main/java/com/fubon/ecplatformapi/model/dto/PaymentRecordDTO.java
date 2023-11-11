package com.fubon.ecplatformapi.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PaymentRecordDTO {
    private String insLin;
    private String insDesc;
    private String polyNoQ;
    private String polyNo;
    private String tradeNo;
    private BigDecimal payAmt;
    private String isrId;
    private String isrNm;
    private String insId;
    private String insNm;
    private Date effDa;
    private Date expDa;
    private Date pcllDa;
    private String fundKind;
    private String iscAdmin;
    private String iscXref;
    private String prsNid;
}
