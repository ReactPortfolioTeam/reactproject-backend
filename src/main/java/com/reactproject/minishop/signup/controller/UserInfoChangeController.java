package com.reactproject.minishop.signup.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactproject.minishop.common.responseType.ErrorMsgVo;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;
import com.reactproject.minishop.signup.service.UserInfoService;
import com.reactproject.minishop.signup.vo.UserInfoChangeVo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/userinfo",produces =MediaType.APPLICATION_JSON_VALUE )
@CrossOrigin("*")
public class UserInfoChangeController {

	private final UserInfoService service;

	
	@PatchMapping("/{userid}")
	public ResponseEntity<?> updateUserInfo(@PathVariable String userid,@Validated @RequestBody UserInfoChangeVo vo,BindingResult error) {
		
		if(!service.checkIfUserExist(userid)){
			ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
			msg.setIssuedAt(new Date());
			msg.setMsg("존재하지 않는 사용자입니다");
			msg.setStatusCode(404);
			return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg,HttpStatus.NOT_FOUND);
		}
				
		if((vo.getPassword()!=null&&vo.getConfirmPw()==null)||(vo.getPassword()==null&&vo.getConfirmPw()!=null)) {
			
			error.rejectValue("password",null,"비밀번호를 입력해주세요");
			
		    }
		
			if(vo.getPassword()!=null&&vo.getConfirmPw()!=null&&!vo.getPassword().equals(vo.getConfirmPw())) {
				error.rejectValue("password", null,"비밀번호를 확인해주세요");
			}
			
			if(error.hasErrors()) {
				
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		    
			}
		
		vo.setUserid(userid);
		
		service.updateUserInfo(vo);		
		
		System.out.println(vo);
	    return new ResponseEntity<String>("회원정보 변경에 성공하였습니다", HttpStatus.ACCEPTED);
		
	}
}
