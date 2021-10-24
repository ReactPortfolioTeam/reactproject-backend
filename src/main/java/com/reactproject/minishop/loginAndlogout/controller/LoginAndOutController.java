package com.reactproject.minishop.loginAndlogout.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactproject.minishop.common.responseType.ErrorMsgVo;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccess;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccessWithRefreshToken;
import com.reactproject.minishop.loginAndlogout.dto.RefreshTokenWithUseridDto;
import com.reactproject.minishop.loginAndlogout.service.LoginAndLogoutService;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;
import com.reactproject.minishop.loginAndlogout.vo.RefreshToken;
import com.reactproject.minishop.loginAndlogout.vo.ResponseTypeForLoginSuccessVo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class LoginAndOutController {

	private final LoginAndLogoutService service;
	
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshToken token){
		System.out.println(token.getRefreshToken());
		String newAccesstoken = service.checkIfRefreshTokenIsValid(token);
		
		if(newAccesstoken!=null) {
			
			ResponseTypeForCommonSuccessWithRefreshToken msg = new ResponseTypeForCommonSuccessWithRefreshToken();
			msg.setIssuedAt(new Date());
			msg.setMsg("토큰이 재발급되었습니다");
			msg.setStatusCode(200);
			msg.setToken(newAccesstoken);
			
			return new ResponseEntity<ResponseTypeForCommonSuccessWithRefreshToken>(msg,HttpStatus.ACCEPTED);

		}
	
		ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
		msg.setIssuedAt(new Date());
		msg.setMsg("유효하지 않은 토큰입니다");
		msg.setStatusCode(400);
		
		return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg,HttpStatus.ACCEPTED);
	}
	
	
	@Transactional
	@PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginHandler(HttpServletResponse response ,@Validated @RequestBody LoginFormVo vo, BindingResult error) throws IllegalArgumentException, NotFoundException {
		

		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		try {
		LoginUserInfoVo userinfo = service.checkIfRequestedUserExist(vo);
		userinfo.setToken(service.generateToken(userinfo));
		userinfo.setRefreshToken(service.generateRefreshToken(userinfo));
		
		service.insertRefreshTokenIntoDatabase(new RefreshTokenWithUseridDto(userinfo.getRefreshToken(), userinfo.getUserid()));
		Cookie cookie = service.createCookieWithRefreshToken(userinfo.getRefreshToken());
		
		response.addCookie(cookie);
		
		ResponseTypeForLoginSuccessVo res = new ResponseTypeForLoginSuccessVo();
		res.setIssuedAt(new Date());
		res.setMsg(userinfo.getUserid()+"님이 로그인에 성공하셨습니다");
		res.setStatusCode(200);
		res.setUserInfo(userinfo);
		
		return new ResponseEntity<ResponseTypeForLoginSuccessVo>(res,HttpStatus.ACCEPTED);
		}
		catch(IllegalArgumentException | NotFoundException e) {
			ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
			msg.setIssuedAt(new Date());
			msg.setMsg(e.getMessage());
			msg.setStatusCode(404);
		    return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg, HttpStatus.BAD_REQUEST);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ResponseTypeForCommonSuccess> logout(HttpServletRequest req){
		String token = req.getHeader("Authorization");
		
		System.out.println(token);
		
		String userId = service.getUserIdFromJwtToken(token);
		
		
		service.deleteAuthInfoByUserId(userId);
		ResponseTypeForCommonSuccess msg = new ResponseTypeForCommonSuccess();
		msg.setIssuedAt(new Date());
		msg.setMsg("로그아웃에 성공하였습니다");
		msg.setStatusCode(200);
		return new ResponseEntity<ResponseTypeForCommonSuccess>(msg,HttpStatus.ACCEPTED);
	}
	
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
