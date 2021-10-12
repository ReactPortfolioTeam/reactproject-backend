package com.reactproject.minishop.loginAndlogout.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;

import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;

public interface LoginAndLogoutService {

	public LoginUserInfoVo checkIfRequestedUserExist(LoginFormVo vo) throws NotFoundException, IllegalArgumentException;
}
