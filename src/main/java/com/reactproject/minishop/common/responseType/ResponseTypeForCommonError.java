package com.reactproject.minishop.common.responseType;

import java.util.List;

import lombok.Data;

@Data
public class ResponseTypeForCommonError extends ResponseTypeForCommon {

	private List<ErrorMsgVo> msg;
}
