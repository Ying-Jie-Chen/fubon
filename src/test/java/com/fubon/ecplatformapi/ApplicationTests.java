package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.token.Token;
import com.fubon.ecplatformapi.token.TokenProperties;
import com.fubon.ecplatformapi.token.TokenService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.crypto.SecretKey;

@Slf4j
@SpringBootTest
@ComponentScan(basePackageClasses = TokenProperties.class)
class ApplicationTests {



	@Test
	void testCreateToken() throws Exception {

		try {


//			Token refreshToken = tokenService.updateToken(token, AESKey);
//			log.info("更新的token: " + refreshToken.getToken());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
