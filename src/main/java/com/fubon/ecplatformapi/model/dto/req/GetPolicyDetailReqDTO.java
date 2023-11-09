package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
public class GetPolicyDetailReqDTO {
    private String queryType;
    private String policyNum;
    private String cls;
}
