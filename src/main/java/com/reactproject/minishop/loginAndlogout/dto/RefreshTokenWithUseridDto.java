package com.reactproject.minishop.loginAndlogout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenWithUseridDto {

	private String refreshToken;
	private String userid;
	
}
