package com.fubon.ecplatformapi.config;

import com.fubon.ecplatformapi.mapper.PolicyDetailMapper;
import org.modelmapper.ModelMapper;
import com.fubon.ecplatformapi.mapper.ResultMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public ResultMapper resultMapper() {
        return new ResultMapper();
    }

    @Bean
    public PolicyDetailMapper policyDetailMapper() {
        return new PolicyDetailMapper();
    }

}
