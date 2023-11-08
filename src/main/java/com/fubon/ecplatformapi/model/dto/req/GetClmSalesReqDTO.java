package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "policy-detail.get-cls-sales")
public class GetClmSalesReqDTO {
    private String query_SalesId;
    private String query_Plan;
    private String query_PolyNo;
}
