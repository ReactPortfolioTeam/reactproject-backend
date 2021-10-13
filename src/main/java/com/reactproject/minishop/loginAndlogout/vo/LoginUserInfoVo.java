package com.reactproject.minishop.loginAndlogout.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LoginUserInfoVo {

	private String userid;
	@JsonIgnore
	private String password;
	private String email;
	private String address;
	private String level;
	private String Token;
	private String refreshToken;
}
