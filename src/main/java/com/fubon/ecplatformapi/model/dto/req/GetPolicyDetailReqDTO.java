package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "policy-detail.get-policy-detail")
public class GetPolicyDetailReqDTO {
    private String queryType;
    private String policyNum;
    private String cls;
}
