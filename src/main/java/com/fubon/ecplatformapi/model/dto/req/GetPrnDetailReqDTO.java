package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
public class GetPrnDetailReqDTO {
    private String queryType;
    private String formatid;
    private String cls;
}
