package com.reactproject.minishop.signup.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reactproject.minishop.common.responseType.ResponseTypeForCommon;
import com.reactproject.minishop.signup.service.UserInfoService;
import com.reactproject.minishop.signup.vo.IdDuplicateValidationVo;
import com.reactproject.minishop.signup.vo.SignupForm;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/signup")
public class SignupController {
	
	private final UserInfoService service;
	
	
	@PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseTypeForCommon> signup(@Validated @RequestBody SignupForm form, BindingResult error){
		System.out.println(form);
			List<String> errormsg = new ArrayList<>();
			
			if(!form.getPassword().equals(form.getConfirmPw())) {
				error.rejectValue("password", null,"비밀번호를 확인해주세요");
			}
			
			if(error.hasErrors()) {
			
			ResponseTypeForCommon errorMsg = new ResponseTypeForCommon();
		    List<ObjectError> errorList = error.getAllErrors();
		    errorList.forEach(a->System.out.println(a));
		    errormsg= errorList.stream().map(a->a.getDefaultMessage()).collect(Collectors.toList());
		   
		    
		    errorMsg.setStatusCode(400);
		    errorMsg.setMsg(errormsg);
		    errorMsg.setIssuedAt(new Date());
		    return new ResponseEntity<ResponseTypeForCommon>(errorMsg, HttpStatus.BAD_REQUEST);
		    
		}
		
		ResponseTypeForCommon msg = new ResponseTypeForCommon();
		List<String> msgForCreated = new ArrayList<>();
		msgForCreated.add(String.format("%s님이 회원가입에 성공하셨습니다", form.getUserid()));
		msg.setStatusCode(201);
		msg.setMsg(msgForCreated);
		msg.setIssuedAt(new Date());
		
		service.registerUser(form.toDto());
		
		//저장.
		
		return new ResponseEntity<ResponseTypeForCommon>(msg, HttpStatus.CREATED);
	}
	
	@GetMapping(path="/check",produces = MediaType.APPLICATION_JSON_VALUE, consumes =MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ResponseTypeForCommon> idDuplicationCheck(@Validated @RequestBody IdDuplicateValidationVo vo,BindingResult error){
		
		ResponseTypeForCommon msg = new ResponseTypeForCommon();
		msg.setIssuedAt(new Date());
		List<String> msgs = new ArrayList<>();
		
		if(error.hasErrors()) {
			List<ObjectError> errorList = error.getAllErrors();
		    errorList.forEach(a->System.out.println(a));
		    msgs = errorList.stream().map(a->a.getDefaultMessage()).collect(Collectors.toList());
		    
		    msg.setIssuedAt(new Date());
		    msg.setMsg(msgs);
		    msg.setStatusCode(400);
		    
			return new ResponseEntity<ResponseTypeForCommon>(msg, HttpStatus.BAD_REQUEST);
		}
		
		if(service.checkIfUseidIsDuplicate(vo.getUserid())==null) {
			msgs.add("사용가능한 아이디입니다");
			msg.setMsg(msgs);
			msg.setStatusCode(200);
			return new ResponseEntity<ResponseTypeForCommon>(msg, HttpStatus.ACCEPTED);
		};
		msgs.add("이미 사용중인 아이디입니다");
		msg.setMsg(msgs);
		msg.setStatusCode(400);
		return new ResponseEntity<ResponseTypeForCommon>(msg, HttpStatus.BAD_REQUEST);

	}
	
	
}
