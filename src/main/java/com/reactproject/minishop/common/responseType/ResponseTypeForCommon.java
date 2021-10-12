package com.reactproject.minishop.common.responseType;

import java.util.Date;

import lombok.Data;

@Data
public abstract class ResponseTypeForCommon {

	private int statusCode;
	private Date issuedAt;

}
