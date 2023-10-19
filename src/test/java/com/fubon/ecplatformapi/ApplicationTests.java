package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.token.CreateToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void testCreateToken() {

		String sessionId = "SessionId";
		String empNo = "EmpNo";
		long timestamp = System.currentTimeMillis();

		try {
			String token = CreateToken.createToken(sessionId, empNo, timestamp);
			System.out.println("token: " + token);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
