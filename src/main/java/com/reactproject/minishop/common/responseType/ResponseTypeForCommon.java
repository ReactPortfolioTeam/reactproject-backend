package com.reactproject.minishop.common.responseType;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public abstract class ResponseTypeForCommon {

	private int statusCode;
	private Date issuedAt;
	
	public ResponseTypeForCommon() {
		// TODO Auto-generated constructor stub
	}
	
	

}
