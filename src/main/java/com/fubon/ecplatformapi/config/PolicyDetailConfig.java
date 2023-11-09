package com.fubon.ecplatformapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "policy-detail")
public class PolicyDetailConfig {

    private String domain;
}
