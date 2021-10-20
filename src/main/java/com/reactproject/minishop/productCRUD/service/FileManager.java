package com.reactproject.minishop.productCRUD.service;

import org.springframework.stereotype.Service;

@Service
public class FileManager {

	public void verify(String contentType) throws IllegalArgumentException {
		
		if(!contentType.contains("image")) {
			throw new IllegalArgumentException("올바르지 않은 형식의 데이터입니다 "+contentType);
		}
	}

}
