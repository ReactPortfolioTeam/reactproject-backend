package com.reactproject.minishop.signup.service;

import com.reactproject.minishop.common.AbstractGlobalInputErrorInterface;
import com.reactproject.minishop.signup.dto.UserSignupInfoDto;
import com.reactproject.minishop.signup.vo.UserInfoChangeVo;


public interface UserInfoService extends AbstractGlobalInputErrorInterface {

	public void registerUser(UserSignupInfoDto userInfo);
	public String checkIfUseidIsDuplicate(String userid);
	public void updateUserInfo(UserInfoChangeVo vo);
	public boolean checkIfUserExist(String userid);
	
}
