package com.reactproject.minishop.signup;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.signup.controller.SignupController;
import com.reactproject.minishop.signup.service.UserInfoService;
import com.reactproject.minishop.signup.vo.SignupForm;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

@WebMvcTest(controllers = SignupController.class)
@Slf4j
@Transactional
public class UserSingupTest {

	@MockBean
	private UserInfoService service;
	
	@Autowired
	private MockMvc mvc;

	 @Autowired
	 private ObjectMapper mapper;
	 
	 private SignupForm mockForm;
	 
	 @BeforeEach
	 public void setup() {
		 mockForm = new SignupForm();
		 mockForm.setAddress("수원시 영통구");
		 mockForm.setConfirmPw("123123123");
		 mockForm.setPassword("123123123");
		 mockForm.setUserid("looaakhkh");
		 mockForm.setEmail("lookhkh@nava.com");

	 }
	
	@Test
	@DisplayName("정상적인 상황")
	public void 회원가입() throws Exception {
		
		
		String json = mapper.writeValueAsString(mockForm);
		
		final ResultActions actions = mvc.perform(post("/api/v1/signup")
										  .contentType(MediaType.APPLICATION_JSON)
										  .content(json)).andExpect(status().isCreated());
										  
		
	}
	
	@Test
	@DisplayName("인수 오류 상황 (아이디에 한글 포함)")
	public void 회원가입인수오류() throws Exception {
		this.mockForm.setUserid("이것은실패해야합니다");
		
		String json = mapper.writeValueAsString(mockForm);

		
		final ResultActions actions =  mvc.perform(post("/api/v1/signup")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(json)).andExpect(status().isBadRequest());			  
	}
	
	@Test
	@DisplayName("인수 오류 상황(비밀번호가 다름)")
	public void 회원가입인수오류2() throws Exception {
		this.mockForm.setPassword("fdsfadfas32234");
		
		String json = mapper.writeValueAsString(mockForm);

		
		final ResultActions actions =  mvc.perform(post("/api/v1/signup")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(json)).andExpect(status().isBadRequest());
				  
	}
	
	
	
}
