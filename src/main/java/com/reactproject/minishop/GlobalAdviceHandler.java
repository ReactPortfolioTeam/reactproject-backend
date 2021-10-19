package com.reactproject.minishop;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;

@RestControllerAdvice
public class GlobalAdviceHandler {

	@ExceptionHandler(value =  {HttpMessageNotReadableException.class,MissingServletRequestParameterException.class})
	public ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg> handlerForNoRequestBodyError(Exception e){
		e.printStackTrace();
		ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
		msg.setIssuedAt(new Date());
		msg.setMsg("값을 입력해주세요");
		msg.setStatusCode(400);
		return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {HttpMediaTypeNotSupportedException.class,HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg> handlerForUnsupportedError(Exception e){
		e.printStackTrace();
		ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
		msg.setIssuedAt(new Date());
		msg.setMsg("API 스팩을 확인해주세요");
		msg.setStatusCode(400);
		return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg,HttpStatus.BAD_REQUEST);
	}
	
}
