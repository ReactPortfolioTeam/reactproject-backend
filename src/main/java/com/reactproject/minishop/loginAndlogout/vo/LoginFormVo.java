package com.reactproject.minishop.loginAndlogout.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class LoginFormVo {
	
	@NotNull(message= "{NotNull}")
	@Length(min=8,message="{Length.id}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{OnlyEngAndNum}")
	private String userid;
	
	@NotNull(message= "{NotNull}")
	@NotEmpty(message="{Empty}")
	@Length(min=6,message="{Length.pw}")
	private String password;
	
	
	
}


	