package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
public class QueryReqDTO {
    private String insType;
    private String plate;
    private Integer queryType;
    private String insurerName;
    private String insurerId;
    private Date effectDateStart;
    private Date effectDateEnd;
    private String managerId;
    @Size(max = 20)
    private String policyNum;
}
