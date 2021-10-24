package com.reactproject.minishop.common.responseType;

import lombok.Data;

@Data
public class ResponseTypeForCommonSuccessWithRefreshToken extends ResponseTypeForCommonSuccess {

	private String token;
}
