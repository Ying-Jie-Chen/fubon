package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "policy-detail.get-prn-detail")
public class GetPrnDetailReqDTO {
    private String queryType;
    private String formatid;
    private String cls;
}
