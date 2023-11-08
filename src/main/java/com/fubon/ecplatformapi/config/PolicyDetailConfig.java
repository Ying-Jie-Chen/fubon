package com.fubon.ecplatformapi.config;

import com.fubon.ecplatformapi.model.dto.req.GetChkEnrDataReqDTO;
import com.fubon.ecplatformapi.model.dto.req.GetClmSalesReqDTO;
import com.fubon.ecplatformapi.model.dto.req.GetPolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.GetPrnDetailReqDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "policy-detail")
public class PolicyDetailConfig {

    private String domain;
    @Bean
    public GetPolicyDetailReqDTO policyDetailReqDTO(){ return new GetPolicyDetailReqDTO(); }

    @Bean
    public GetPrnDetailReqDTO prnDetailReqDTO(){ return new GetPrnDetailReqDTO(); }

    @Bean
    public GetClmSalesReqDTO clmSalesReqDTO(){ return new GetClmSalesReqDTO(); }

    @Bean
    public GetChkEnrDataReqDTO chkEnrDataReqDTO(){ return new GetChkEnrDataReqDTO(); }
}
