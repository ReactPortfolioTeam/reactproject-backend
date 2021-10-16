package com.reactproject.minishop.signup.dao;

import org.springframework.stereotype.Repository;

import com.reactproject.minishop.signup.dto.UserSignupInfoDto;
import com.reactproject.minishop.signup.vo.UserInfoChangeVo;

@Repository
public interface UserManageMapper {

	public void insertNewUserInfo(UserSignupInfoDto userInfo);

	public String findById(String userid);

	public void updateUserInfo(UserInfoChangeVo vo);
	
}
