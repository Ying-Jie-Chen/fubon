package com.fubon.ecplatformapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sso-login")
public class SsoLoginConfig {

    private SSOLoginInfo test = new SSOLoginInfo();

    private SSOLoginInfo production = new SSOLoginInfo();

    @Data
    public static class SSOLoginInfo {

        private String webServiceAcc;

        private String webServicePwd;
    }
}
