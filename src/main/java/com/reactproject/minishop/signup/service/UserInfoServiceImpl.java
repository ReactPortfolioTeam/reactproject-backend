package com.reactproject.minishop.signup.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.reactproject.minishop.signup.dao.UserManageMapper;
import com.reactproject.minishop.signup.dto.UserSignupInfoDto;

import lombok.AllArgsConstructor;
;
@AllArgsConstructor
@Service
@Primary
public class UserInfoServiceImpl implements UserInfoService {

	private final UserManageMapper mapper;
	
	@Override
	public void registerUser(UserSignupInfoDto userInfo) {
		System.out.println(userInfo);
		mapper.insertNewUserInfo(userInfo);
	}
	
	@Override
	public String checkIfUseidIsDuplicate(String userid) {
		String checkid = mapper.findById(userid);
		return StringUtils.hasText(checkid)?checkid:null;
	}
}