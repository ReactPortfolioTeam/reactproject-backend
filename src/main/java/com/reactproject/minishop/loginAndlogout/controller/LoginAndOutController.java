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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
		

		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
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
	

	
	//익셉션 처리 필요
	
	@ExceptionHandler(value = {NotFoundException.class,IllegalArgumentException.class})
	public ResponseEntity<ResponseTypeForCommonError> errorHanddlerForLoginAndOutController(Exception e){
		
		
		HttpStatus status;
		
		if(e.getClass().equals(IllegalArgumentException.class)) {
			status = HttpStatus.BAD_REQUEST;
		}else {
			status = HttpStatus.NOT_FOUND;
		}
		
		List<ErrorMsgVo> errors = new ArrayList<>();
		ErrorMsgVo vo = new ErrorMsgVo("global",e.getMessage());
		errors.add(vo);
		
		
		ResponseTypeForCommonError error = new ResponseTypeForCommonError();
		error.setIssuedAt(new Date());
		error.setStatusCode(404);
		error.setMsg(errors);
		
		
		return new ResponseEntity<ResponseTypeForCommonError>(error,status);
	}
	
}
