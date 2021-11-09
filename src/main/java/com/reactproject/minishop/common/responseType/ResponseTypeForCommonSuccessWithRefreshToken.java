package com.reactproject.minishop.common.responseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseTypeForCommonSuccessWithRefreshToken extends ResponseTypeForCommonSuccess {

	private String token;
}
