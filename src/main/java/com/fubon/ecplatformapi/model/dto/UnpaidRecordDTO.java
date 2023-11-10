package com.fubon.ecplatformapi.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UnpaidRecordDTO {

    @Size(max = 3)
    private String insLin;

    @Size(max = 10)
    @Nullable
    private String insDesc;

    @Size(max = 10)
    @Nullable
    private String polyNoQ;

    @Size(max = 16)
    private String polyNo;


    private Integer seqNo;

    @Size(max = 16)
    private String tradeNo;

    @DecimalMax(value = "11")
    private BigDecimal preMiu;

    @Size(max = 20)
    @Nullable
    private String isrId;

    @Size(max = 160)
    @Nullable
    private String isrNm;

    @Size(max = 20)
    @Nullable
    private String insId;

    @Size(max = 160)
    @Nullable
    private String insNm;

    @Size(max = 11)
    private String platNo;

    @Nullable
    private Date issDa;

    @Nullable
    private Date effDa;

    @Nullable
    private Date expDa;

    @Size(max = 7)
    @Nullable
    private String iscAdmin;

    @Size(max = 9)
    @Nullable
    private String iscXref;

    @Size(max = 20)
    @Nullable
    private String prsnId;

    @Size(max = 3)
    @Nullable
    private String addrCode;

    @Size(max = 200)
    @Nullable
    private String addr;

}
