package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "policy-detail.get-chk-enr-data")
public class GetChkEnrDataReqDTO {
    private String fotmatid;
}
