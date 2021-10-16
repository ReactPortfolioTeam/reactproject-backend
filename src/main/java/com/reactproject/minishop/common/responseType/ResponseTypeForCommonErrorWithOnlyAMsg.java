package com.reactproject.minishop.common.responseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTypeForCommonErrorWithOnlyAMsg extends ResponseTypeForCommon {

	private String msg;
	
}
