package com.reactproject.minishop.loginAndLogout;

import java.util.Date;

import javax.naming.directory.InvalidAttributesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reactproject.minishop.common.enumtype.UserStatus;
import com.reactproject.minishop.loginAndlogout.service.JwtTokenManager;

public class LogoutTestWithFilter {

	private JwtTokenManager manager;
	
	@BeforeEach
	public void init() {
		manager = new JwtTokenManager();
	}
	
	@Test
	@DisplayName("비공개 API 호출 시, 필터를 거쳐서 토큰의 유효성을 검증")
	public void test() throws Exception {
		String token = manager.generateJwtStringWith("lookhkh", UserStatus.MEMBER);
		String refrshToken = manager.generateJwtRefreshStringTokenWith("lookhkh");
		
		manager.decodeJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQyMDU0NDcsInVzZXJpZCI6Imxkb29raGtkaCIsInN0YXR1cyI6Ik1FTUJFUiJ9.fEyLNvS073OZmWvLOfjADl33hi-Ok8M_l2LaPCKdczY", new Date());
	

	}
}
