package com.reactproject.minishop.common.responseType;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMsgVo {
	private String field;
	private String msg;
}
