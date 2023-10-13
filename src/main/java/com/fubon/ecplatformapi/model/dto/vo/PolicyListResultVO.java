package com.fubon.ecplatformapi.model.dto.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyListResultVO {

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
        private Date effectDate;

        @JsonProperty("secExpdate")
        private Date expireDate;

}

