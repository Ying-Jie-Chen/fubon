package com.fubon.ecplatformapi.config;

import org.modelmapper.ModelMapper;
import com.fubon.ecplatformapi.ResultMapper;
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

}
