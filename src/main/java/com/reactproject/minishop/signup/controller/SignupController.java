package com.reactproject.minishop.signup.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			
			if(!form.getPassword().equals(form.getConfirmPw())) {
				error.rejectValue("password", null,"비밀번호를 확인해주세요");
			}
			
			if(error.hasErrors()) {
				
			ResponseTypeForCommonError errorMsg = extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		    
		}
		
		ResponseTypeForCommonSuccess msg = new ResponseTypeForCommonSuccess();
		msg.setStatusCode(201);
		msg.setMsg(String.format("%s님이 회원가입에 성공하셨습니다", form.getUserid()));
		msg.setIssuedAt(new Date());
		
		service.registerUser(form.toDto());
		
		//저장.
		
		return new ResponseEntity<ResponseTypeForCommonSuccess>(msg, HttpStatus.CREATED);
	}
	
	@GetMapping(path="/check",produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<? extends ResponseTypeForCommon> idDuplicationCheck(@Validated @RequestBody IdDuplicateValidationVo vo,BindingResult error){
		
		ResponseTypeForCommonError msg = new ResponseTypeForCommonError();
		msg.setIssuedAt(new Date());
		List<ErrorMsgVo> msgs = new ArrayList<>();
		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		if(service.checkIfUseidIsDuplicate(vo.getUserid())==null) {
			ResponseTypeForCommonSuccess success = new ResponseTypeForCommonSuccess();
			success.setIssuedAt(new Date());
			success.setMsg("사용가능한 아이디입니다");
			success.setStatusCode(200);
			return new ResponseEntity<ResponseTypeForCommonSuccess>(success, HttpStatus.ACCEPTED);
		};
		
		msgs.add(new ErrorMsgVo("global","이미 사용중인 아이디입니다"));
		msg.setStatusCode(400);
		msg.setMsg(msgs);
		return new ResponseEntity<ResponseTypeForCommonError>(msg, HttpStatus.BAD_REQUEST);

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
	
	
}
