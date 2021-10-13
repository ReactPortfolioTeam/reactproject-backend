package com.reactproject.minishop.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import javax.naming.directory.InvalidAttributesException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.common.enumtype.UserStatus;
import com.reactproject.minishop.loginAndlogout.service.JwtTokenManager;

public class JwtTokenTest {

	private final JwtTokenManager manager = new JwtTokenManager();
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void JWT_키를_잘_불러오는지_확인() {
		String key = manager.getKey();
		assertThat(key).isNotNull();
		assertThat(key).isNotBlank();

	}
	
	@Test
	public void jwt_엑세스토큰_생성테스트() throws JsonMappingException, JsonProcessingException {
		String token = manager.generateJwtStringWith("lookhkh", UserStatus.MEMBER);
		
		System.out.println(token);
		
		assertThat(token).isNotNull();
		assertThat(token).isNotBlank();
		
	}
	
	@Test
	public void jwt_refrsh_token_생성테스트() {
		String refreshToken = manager.generateJwtRefreshStringTokenWith("lookhkh");
		assertThat(refreshToken).isNotNull();
	}
	
	@Test
	public void jwt디코딩_유효시간이내의_토큰의_유효성_확인() throws InvalidAttributesException {
		
		String userid = "lookhkh";
		UserStatus status = UserStatus.MEMBER;
		
		String token = manager.generateJwtStringWith(userid, status);
		String refrshToken = manager.generateJwtRefreshStringTokenWith(userid);
		
		assertThat(manager.decodeJwt(token,new Date())).isTrue(); //유효시간이 남은 경우
		assertThat(manager.decodeJwt(refrshToken, new Date())).isTrue();
		
		Date forNow = new Date();
		Calendar before30minutes = Calendar.getInstance();
		before30minutes.setTime(forNow);
		before30minutes.add(Calendar.MINUTE, +31); //유효시간이 지난 경우
		
		forNow.setTime(before30minutes.getTimeInMillis());
		
		System.out.println(forNow);
		
		assertThat(manager.decodeJwt(token, forNow)).isFalse(); //유효시간이 지난 경우, false를 반환해야 한다.
		
		Calendar in9Hour = Calendar.getInstance();
		in9Hour.setTime(forNow);
		in9Hour.add(Calendar.HOUR, 10); //유효시간이 지난 경우
		
		forNow.setTime(in9Hour.getTimeInMillis());
		
		assertThat(manager.decodeJwt(refrshToken, forNow)).isFalse(); //유효시간이 지난 경우, false를 반환해야 한다.

		
	}
}
