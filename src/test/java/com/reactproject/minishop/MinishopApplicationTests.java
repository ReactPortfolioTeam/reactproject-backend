package com.reactproject.minishop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinishopApplicationTests {

	@Value("${jwtkey}")
	private String key;
	
	@Test
	void contextLoads() {
		System.out.println(key);
		
	}

}
