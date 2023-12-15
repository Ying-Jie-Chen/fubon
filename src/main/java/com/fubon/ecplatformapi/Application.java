package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.properties.CleanUpProperties;
import com.fubon.ecplatformapi.properties.TokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
//@EnableConfigurationProperties({TokenProperties.class, CleanUpProperties.class})
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}
