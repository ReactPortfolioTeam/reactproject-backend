package com.reactproject.minishop.loginAndlogout.vo;

import java.util.Date;

import lombok.Data;

@Data
public class RefreshToken {

	private String userId;
	private String refreshToken;
	private Date expireDate;
}
