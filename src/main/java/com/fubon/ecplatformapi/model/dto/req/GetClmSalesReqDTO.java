package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
public class GetClmSalesReqDTO {
    private String query_SalesId;
    private String query_Plan;
    private String query_PolyNo;
}
