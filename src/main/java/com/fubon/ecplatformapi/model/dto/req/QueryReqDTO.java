package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fubon.ecplatformapi.ParameterValid;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
public class QueryReqDTO implements Serializable {

    //@ParameterValid(values = {"MOT", "CQCCX", "CHCRX", "CTX", "CGX", "FIR", "ENG", "MGO", "CAS"})

    @NotNull(message = "insType 不可為空值")
    private String insType;

    @NotNull(message = "plate 不可為空值")
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
