package com.reactproject.minishop.signup.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSignupInfoDto {

	private String userid;
	private String password;
	private String name;
	private String email;
	private String address;

}
