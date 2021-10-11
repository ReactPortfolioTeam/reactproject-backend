package com.reactproject.minishop.signup.vo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.reactproject.minishop.signup.dto.UserSignupInfoDto;

import lombok.Data;

@Data
public class SignupForm {
	
	@NotNull(message= "{NotNull}")
	@Length(min=8,message="{Length.id}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{OnlyEngAndNum}")
	private String userid;
	@NotNull(message= "{NotNull}")
	@NotEmpty(message="{Empty}")
	@Length(min=6,message="{Length.pw}")
	private String password;
	@NotNull(message= "{NotNull}")
	@NotEmpty(message="{Empty}")
	@Length(min=6,message="{Length.pw}")
	private String confirmPw;
	@NotNull(message= "{NotNull}")
	@NotEmpty(message="{Empty}")
	@Email(message="{Email}")
	private String email;
	@NotNull(message= "{NotNull}")
	@NotEmpty(message="{Empty}")
	private String address;
	
	public UserSignupInfoDto toDto() {
		UserSignupInfoDto dto = new UserSignupInfoDto();
		dto.setUserid(this.userid);
		dto.setPassword(this.password);
		dto.setAddress(this.address);
		dto.setEmail(this.email);
		
		return dto;
	}
	
}
