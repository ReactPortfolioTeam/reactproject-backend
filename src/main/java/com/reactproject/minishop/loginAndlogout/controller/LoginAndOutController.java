package com.reactproject.minishop.loginAndlogout.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactproject.minishop.common.responseType.ErrorMsgVo;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;
import com.reactproject.minishop.loginAndlogout.dto.RefreshTokenWithUseridDto;
import com.reactproject.minishop.loginAndlogout.service.LoginAndLogoutService;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;
import com.reactproject.minishop.loginAndlogout.vo.ResponseTypeForLoginSuccessVo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class LoginAndOutController {

	private final LoginAndLogoutService service;
	
	@Transactional
	@PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginHandler(@Validated @RequestBody LoginFormVo vo, BindingResult error) throws IllegalArgumentException, NotFoundException {
		
		ResponseTypeForCommonError msg = new ResponseTypeForCommonError();
		msg.setIssuedAt(new Date());
		List<ErrorMsgVo> msgs = new ArrayList<>();
		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		
		LoginUserInfoVo userinfo = service.checkIfRequestedUserExist(vo);
		userinfo.setToken(service.generateToken(userinfo));
		userinfo.setRefreshToken(service.generateRefreshToken(userinfo));
		
		service.insertRefreshTokenIntoDatabase(new RefreshTokenWithUseridDto(userinfo.getRefreshToken(), userinfo.getUserid()));
		
		
		ResponseTypeForLoginSuccessVo res = new ResponseTypeForLoginSuccessVo();
		res.setIssuedAt(new Date());
		res.setMsg(userinfo.getUserid()+"님이 로그인에 성공하셨습니다");
		res.setStatusCode(200);
		res.setUserInfo(userinfo);
		
		
		
		return new ResponseEntity<ResponseTypeForLoginSuccessVo>(res,HttpStatus.ACCEPTED);
	}
	
	private ResponseTypeForCommonError extractErrorMsgFromErrorObject(BindingResult error) {
		List<FieldError> fields = error.getFieldErrors();
		List<ErrorMsgVo> errorLists=fields.stream().map(a->new ErrorMsgVo(a.getField(),a.getDefaultMessage())).collect(Collectors.toList());

		ResponseTypeForCommonError errorMsg = new ResponseTypeForCommonError();
		
		
		errorMsg.setStatusCode(400);
		errorMsg.setMsg(errorLists);
		errorMsg.setIssuedAt(new Date());
		return errorMsg;
	}
	
	//익셉션 처리 필요
	

	
}
