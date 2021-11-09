package com.reactproject.minishop.common.responseType;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseTypeForCommonSuccessWithImgPath extends ResponseTypeForCommonSuccess {

	private List<String> imgPath;
	
	
}
