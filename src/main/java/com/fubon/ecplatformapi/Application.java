package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.token.TokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TokenProperties.class)
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}
