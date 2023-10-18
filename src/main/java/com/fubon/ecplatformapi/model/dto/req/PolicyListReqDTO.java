package com.fubon.ecplatformapi.model.dto.req;

import com.fubon.ecplatformapi.InsTypeValidation;
import com.fubon.ecplatformapi.PlateValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@PlateValidation
public class PolicyListReqDTO {

    @NotNull(message = "insType 不可為空值")
    @InsTypeValidation
    private String insType;

    private String plate;

    @NotNull(message = "queryType 不可為空值")
    @Min(value = 0, message = "queryType 只能為0或1")
    @Max(value = 1, message = "queryType 只能為0或1")
    private Integer queryType;

    @NotNull(message = "insurerName 不可為空值")
    private String insurerName;

    @NotNull(message = "insurerId 不可為空值")
    private String insurerId;

    @NotNull(message = "effectDateStart 不可為空值")
    private Date effectDateStart;

    @NotNull(message = "effectDateEnd 不可為空值")
    private Date effectDateEnd;

    @NotNull(message = "managerId 不可為空值")
    private String managerId;

    @NotNull(message = "policyNum 不可為空值")
    @Size(max = 20, message = "policyNum 最大長度為20")
    private String policyNum;

}
