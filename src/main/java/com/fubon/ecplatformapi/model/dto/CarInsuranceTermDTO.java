package com.fubon.ecplatformapi.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CarInsuranceTermDTO {

    @Size(max = 20)
    @Nullable
    private Long prjCode;

    @Size(max = 4)
    @Nullable
    private String termInsCode;

    @Size(max = 200)
    private String termInsName;

    @Size(max = 500)
    private String termInsNote1;

    @Size(max = 500)
    private String termInsContent;

    @Size(max = 500)
    private String termInsLayout1;

    @Size(max = 500)
    private String termInsDefault1;

    @Size(max = 500)
    private String termInsContent1;

    @Size(max = 500)
    private String termInsNote2;

    @Size(max = 500)
    private String termInsLayout2;

    @Size(max = 500)
    private String termInsDefault2;

    @Size(max = 500)
    private String termInsContent2;

    @Size(max = 500)
    private String termInsNote3;

    @Size(max = 500)
    private String termInsLayout3;

    @Size(max = 500)
    private String termInsDefault3;

    private Date cTime;

    private Date mTime;

    @Size(max = 4)
    private String termInsLsAction;

    @Size(max = 500)
    private String termInsCarTypeLimit;

    @Size(max = 100)
    private String termInsApiParam1;

    @Size(max = 100)
    private String termInsApiParam2;

    @Size(max = 100)
    private String termInsApiParam3;

    @Size(max = 100)
    private String termInsUnit1;

    @Size(max = 100)
    private String termInsUnit2;

    @Size(max = 100)
    private String termInsUnit3;

    @Size(max = 1000)
    private String termMemo;

    private Date termInsStopTime;

    @Size(max = 100)
    private String termInsFlagOthers;

    private Integer termInsLenRPoint;

    @Size(max = 50)
    private String termInsWrhflGrp;

}
