package com.reactproject.minishop.signup.service;

import org.springframework.stereotype.Repository;

import com.reactproject.minishop.signup.dto.UserSignupInfoDto;


public interface UserInfoService {

	public void registerUser(UserSignupInfoDto userInfo);
	public String checkIfUseidIsDuplicate(String userid);
}
