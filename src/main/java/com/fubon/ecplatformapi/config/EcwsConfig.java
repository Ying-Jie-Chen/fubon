package com.fubon.ecplatformapi.config;

import com.fubon.ecplatformapi.model.dto.req.FubonLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonSsoTokenDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonVerificationReqDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@ConfigurationProperties(prefix = "ecws")
public class EcwsConfig {

    private String domain;

    private EcWsHeaderConfig header;

    @Bean
    public FubonLoginReqDTO fubonLoginConfig() {
        return new FubonLoginReqDTO();
    }

    @Bean
    public FubonVerificationReqDTO verificationConfig() {
        return new FubonVerificationReqDTO();
    }

    @Bean
    public FubonSsoTokenDTO fubonSsoTokenDTO(){ return  new FubonSsoTokenDTO(); }

    @Data
    public static class EcWsHeaderConfig {
        private String fromSys;
        private String sysPwd;
    }
}
