package com.reactproject.minishop.loginAndlogout.vo;

import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccess;

import lombok.Data;

@Data
public class ResponseTypeForLoginSuccessVo extends ResponseTypeForCommonSuccess {

	private LoginUserInfoVo userInfo;
}
