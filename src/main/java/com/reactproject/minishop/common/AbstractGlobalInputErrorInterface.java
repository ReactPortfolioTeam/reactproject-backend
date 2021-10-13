package com.reactproject.minishop.common;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.reactproject.minishop.common.responseType.ErrorMsgVo;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;

public interface AbstractGlobalInputErrorInterface {
/*
 *유저입력 밸리데이션 공통 처리 디폴트 메소드 
 *유저입력을 받는 컨트롤러에 의존되는 모든 서비스는 해당 인터페이스를 extends
 *그 이후, BindingResult 타입의 error 객체를 파라미터로 사용 
 * */
	public default ResponseTypeForCommonError extractErrorMsgFromErrorObject(BindingResult error) {
		List<FieldError> fields = error.getFieldErrors();
		List<ErrorMsgVo> errorLists=fields.stream().map(a->new ErrorMsgVo(a.getField(),a.getDefaultMessage())).collect(Collectors.toList());

		ResponseTypeForCommonError errorMsg = new ResponseTypeForCommonError();
		
		
		errorMsg.setStatusCode(400);
		errorMsg.setMsg(errorLists);
		errorMsg.setIssuedAt(new Date());
		return errorMsg;
	}
	
}
