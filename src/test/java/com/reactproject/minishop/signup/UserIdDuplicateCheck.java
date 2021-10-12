package com.reactproject.minishop.signup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.signup.controller.SignupController;
import com.reactproject.minishop.signup.service.UserInfoService;
import com.reactproject.minishop.signup.vo.IdDuplicateValidationVo;

@WebMvcTest(controllers = SignupController.class)
public class UserIdDuplicateCheck {

	@MockBean
	private UserInfoService service;
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;
	
	private IdDuplicateValidationVo vo;
	
	 @BeforeEach
	 public void setup() {
		 vo = new IdDuplicateValidationVo();
	 }
	
	
	@Test
	public void 아이디중복체크기능_실패_중복() throws Exception {	
		vo.setUserid("lookhkh1234"); //기존에 존재하는 아이디;
		
		String json = mapper.writeValueAsString(vo);
		
		Mockito.when(service.checkIfUseidIsDuplicate(vo.getUserid())).thenReturn(vo.getUserid());		

		mvc.perform(get("/api/v1/signup/check")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void 아이디중복체크기능_성공() throws Exception {	
		vo.setUserid("lookhkh123455"); //존재하지 않는 아이디
		
		String json = mapper.writeValueAsString(vo);
		
		Mockito.when(service.checkIfUseidIsDuplicate(vo.getUserid())).thenReturn(null);		

		mvc.perform(get("/api/v1/signup/check")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void 아이디중복체크기능_실패_아이디형식오류() throws Exception {	
		vo.setUserid("ㅁㅁㄴㅇㅁㄴㅇ"); //존재하지 않는 아이디
		
		String json = mapper.writeValueAsString(vo);
		
		Mockito.when(service.checkIfUseidIsDuplicate(vo.getUserid())).thenReturn(vo.getUserid());		

		MvcResult result = mvc.perform(get("/api/v1/signup/check")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().is4xxClientError()).andReturn();
		
	}
	
	 
}
