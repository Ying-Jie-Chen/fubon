package com.fubon.ecplatformapi.model.dto.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyListResultVO implements Serializable{

        @JsonProperty("clsGrp")
        private String insType;

        @JsonProperty("polFormatid")
        private String policyNum;

        @JsonProperty("unPaidPrm")
        private Integer premiums;

        @JsonProperty("rmaClinameI")
        private String insuredName;

        @JsonProperty("mohPlatno")
        private String plate;

        @JsonProperty("secEffdate")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date effectDate;

        @JsonProperty("secExpdate")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date expireDate;


}

