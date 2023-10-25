package com.fubon.ecplatformapi.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    @DurationUnit(ChronoUnit.MINUTES)
    private Duration expirationMinutes;

    public void setExpirationMinutes(Duration expirationMinutes) {
        this.expirationMinutes = expirationMinutes;
    }
}
