package com.reactproject.minishop.common.responseType;

import lombok.Data;

@Data
public class ResponseTypeForCommonSuccessWithOrderId extends ResponseTypeForCommon{

	private String msg;
	private int orderId;
}
