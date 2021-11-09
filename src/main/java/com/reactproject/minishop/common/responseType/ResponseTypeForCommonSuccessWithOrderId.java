package com.reactproject.minishop.common.responseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseTypeForCommonSuccessWithOrderId extends ResponseTypeForCommon{

	private String msg;
	private int orderId;
}
