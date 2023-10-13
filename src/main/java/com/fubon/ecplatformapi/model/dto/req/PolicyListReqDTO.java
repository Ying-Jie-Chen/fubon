package com.fubon.ecplatformapi.model.dto.req;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@NoArgsConstructor
public class PolicyListReqDTO {

    private Integer id;

    @NotNull(message = "insType 不可為空值")
    private String insType;

    private String plate;

    @NotNull(message = "queryType 不可為空值")
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
    @Max(value = 20, message = "長度最大為20")
    private String policyNum;

}
