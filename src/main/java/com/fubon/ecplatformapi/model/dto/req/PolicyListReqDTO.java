package com.fubon.ecplatformapi.model.dto.req;

import com.fubon.ecplatformapi.validation.InsTypeValidation;
import com.fubon.ecplatformapi.validation.PlateValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Calendar;
@Data
@NoArgsConstructor
@PlateValidation
public class PolicyListReqDTO {


    @InsTypeValidation
    private String insType;
    private String plate;
    @Min(value = 0, message = "queryType 只能為0或1")
    @Max(value = 1, message = "queryType 只能為0或1")
    private Integer queryType;
    private String insurerName;
    private String insurerId;
    private Calendar effectDateStart;
    private Calendar effectDateEnd;
    private String managerId;
    @Size(max = 20, message = "policyNum 最大長度為20")
    private String policyNum;

}
