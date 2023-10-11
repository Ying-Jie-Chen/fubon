package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fubon.ecplatformapi.ParameterValid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class QueryReqDTO implements Serializable {

    @ParameterValid(values = {"MOT", "CQCCX", "CHCRX", "CTX", "CGX", "FIR", "ENG", "MGO", "CAS"})
    private String insType;
    private String plate;
    private Integer queryType;
    private String insurerName;
    private String insurerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectDateEnd;
    private String managerId;
    @Max(value = 20, message = "長度最大為20")
    private String policyNum;
}
