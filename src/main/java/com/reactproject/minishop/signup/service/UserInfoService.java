package com.reactproject.minishop.signup.service;

import com.reactproject.minishop.common.AbstractGlobalInputErrorInterface;
import com.reactproject.minishop.signup.dto.UserSignupInfoDto;


public interface UserInfoService extends AbstractGlobalInputErrorInterface {

	public void registerUser(UserSignupInfoDto userInfo);
	public String checkIfUseidIsDuplicate(String userid);
	
}
