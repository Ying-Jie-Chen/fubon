package com.fubon.ecplatformapi.config;

import com.fubon.ecplatformapi.helper.JsonHelper;
import com.fubon.ecplatformapi.mapper.InsuranceEntityMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JsonHelper jsonHelper() {
        return new JsonHelper();
    }

}
