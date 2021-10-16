package com.reactproject.minishop.signup.vo;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
@Data
public class UserInfoChangeVo {

	private String userid;
	@Length(min=6,message="{Length.pw}")
	private String password;
	@Length(min=6,message="{Length.pw}")
	private String confirmPw;
	@Email(message="{Email}")
	private String email;
	private String address;
}
