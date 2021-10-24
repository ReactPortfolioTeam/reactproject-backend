package com.reactproject.minishop.common.responseType;

import java.util.List;

import lombok.Data;

@Data
public class ResponseTypeForCommonSuccessWithImgPath extends ResponseTypeForCommonSuccess {

	private List<String> imgPath;
}
