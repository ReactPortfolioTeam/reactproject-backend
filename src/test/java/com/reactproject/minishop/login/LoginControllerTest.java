package com.reactproject.minishop.login;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.loginAndlogout.controller.LoginAndOutController;
import com.reactproject.minishop.loginAndlogout.service.LoginAndLogoutService;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;


@WebMvcTest(LoginAndOutController.class)
public class LoginControllerTest {
	
	 @Autowired
	 private ObjectMapper mapper;
	 
	 private LoginFormVo loginVo;
	/*
	 * 정상적으로 입력한 경우
	 * 1. 입력받은 아이디가 DB에 존재하며, 비밀번호도 동일한 경우 유저정보와 토큰을 리턴
	 * 2. 입력받은 아이디가 DB에 존재하나, 비밀번호가 다른 경우, IllegalArgumentException 에러 
	 * 3. 입력받은 아이디가 DB에 존재하지 않는 경우, NotFoundException
	 * 
	 * 
	 * */
	 
	 @BeforeEach
	 public void setup() {
		 loginVo = new LoginFormVo();
		 
	 }
	
	@MockBean
	LoginAndLogoutService mockService;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	@DisplayName(" 1. 입력받은 아이디가 DB에 존재하며, 비밀번호도 동일한 경우 유저정보와 토큰을 리턴")
	public void test() throws Exception {
		loginVo.setUserid("lookhkh12345");
		loginVo.setPassword("123123123");

		String json = mapper.writeValueAsString(loginVo);
		
		assertIfReturn(json);
		
	}

	@Test
	@DisplayName("2. 입력받은 아이디가 DB에 존재하나, 비밀번호가 다른 경우, IllegalArgumentException 에러")
	public void test1() throws Exception {
		String json = mapper.writeValueAsString(loginVo);

		assertIfThrow(json,IllegalArgumentException.class);
	}
	
	@Test
	@DisplayName("3. 입력받은 아이디가 DB에 존재하지 않는 경우, NotFoundException")
	public void test2() throws Exception{
		String json = mapper.writeValueAsString(loginVo);

		assertIfThrow(json,NotFoundException.class);
	}
	
	
	private <T> void assertIfThrow(String json, Class<? extends Exception> type) throws NotFoundException, Exception {
		Mockito.when(mockService.checkIfRequestedUserExist(Mockito.any())).thenThrow(type);
		mvc.perform(post("/api/v1/login")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8")).andExpect(status().is4xxClientError()).andReturn();
	}
	
	
	private void assertIfReturn(String json) throws NotFoundException, Exception {
		
		LoginUserInfoVo vo = new LoginUserInfoVo();		
		vo.setAddress("경기도");
		vo.setEmail("lookhkh@naver.com");
		vo.setLevel("MEMBER");
		vo.setUserid("lookhkh");
		vo.setRefreshToken("dasdasdasd.asdasdasd.asdasd");
		vo.setRefreshToken("dassad.asdasdas.asdasdasd");
		
		Mockito.when(mockService.checkIfRequestedUserExist(Mockito.any())).thenReturn(vo);		
				mvc.perform(post("/api/v1/login")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8")).andExpect(status().is2xxSuccessful());		
		
	}
	
}
