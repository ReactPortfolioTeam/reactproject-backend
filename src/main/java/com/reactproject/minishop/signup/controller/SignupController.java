package com.reactproject.minishop.signup.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.reactproject.minishop.common.responseType.ErrorMsgVo;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommon;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccess;
import com.reactproject.minishop.signup.service.UserInfoService;
import com.reactproject.minishop.signup.vo.IdDuplicateValidationVo;
import com.reactproject.minishop.signup.vo.SignupForm;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/signup")
@CrossOrigin("*")
public class SignupController {
	
	private final UserInfoService service;
	
	
	@PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends ResponseTypeForCommon> signup(@Validated @RequestBody SignupForm form, BindingResult error){
			
			if(service.checkIfUseidIsDuplicate(form.getUserid())!=null) {
				error.rejectValue("userid", null,"?????? ???????????? ??????????????????");
			};
		
			if(!form.getPassword().equals(form.getConfirmPw())) {
				error.rejectValue("password", null,"??????????????? ??????????????????");
			}
			
			if(error.hasErrors()) {
				
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		    
		}
		
		ResponseTypeForCommonSuccess msg = new ResponseTypeForCommonSuccess();
		msg.setStatusCode(201);
		msg.setMsg(String.format("%s?????? ??????????????? ?????????????????????", form.getUserid()));
		msg.setIssuedAt(new Date());
		
		service.registerUser(form.toDto());
		
		//??????.
		
		return new ResponseEntity<ResponseTypeForCommonSuccess>(msg, HttpStatus.CREATED);
	}
	
	@GetMapping(path="/check",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends ResponseTypeForCommon> idDuplicationCheck(@Validated IdDuplicateValidationVo userid, BindingResult error ){
		

		ResponseTypeForCommonError msg = new ResponseTypeForCommonError();
		msg.setIssuedAt(new Date());
		List<ErrorMsgVo> msgs = new ArrayList<>();

		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		if(service.checkIfUseidIsDuplicate(userid.getUserid())==null) {
			ResponseTypeForCommonSuccess success = new ResponseTypeForCommonSuccess();
			success.setIssuedAt(new Date());
			success.setMsg("??????????????? ??????????????????");
			success.setStatusCode(200);
			return new ResponseEntity<ResponseTypeForCommonSuccess>(success, HttpStatus.ACCEPTED);
		};
		
		msgs.add(new ErrorMsgVo("global","?????? ???????????? ??????????????????"));
		msg.setStatusCode(400);
		msg.setMsg(msgs);
		return new ResponseEntity<ResponseTypeForCommonError>(msg, HttpStatus.BAD_REQUEST);

	}
	

}
