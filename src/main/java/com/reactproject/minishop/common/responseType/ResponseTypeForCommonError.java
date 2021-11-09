package com.reactproject.minishop.common.responseType;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseTypeForCommonError extends ResponseTypeForCommon {

	private List<ErrorMsgVo> msg;
	
	
}
