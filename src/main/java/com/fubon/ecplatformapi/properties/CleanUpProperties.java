//package com.fubon.ecplatformapi.properties;
//
//import lombok.Getter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.convert.DurationUnit;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//
//@Getter
//@Configuration
//@ConfigurationProperties(prefix = "nas")
//public class CleanUpProperties {
//
//    @DurationUnit(ChronoUnit.DAYS)
//    private Duration expirationDays;
//
//    private String cronExpression;
//
//    public void setExpirationDays(Duration expirationDays) {
//        this.expirationDays = expirationDays;
//    }
//    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }
//}
