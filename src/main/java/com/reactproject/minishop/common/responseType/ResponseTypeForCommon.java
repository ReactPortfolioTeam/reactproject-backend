package com.reactproject.minishop.common.responseType;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ResponseTypeForCommon {

	private int statusCode;
	private List<String> msg;
	private Date issuedAt;
}
