package com.reactproject.minishop.loginAndlogout.service;

import org.apache.ibatis.javassist.NotFoundException;

import com.reactproject.minishop.common.AbstractGlobalInputErrorInterface;
import com.reactproject.minishop.loginAndlogout.dto.RefreshTokenWithUseridDto;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;

public interface LoginAndLogoutService extends AbstractGlobalInputErrorInterface {

	public LoginUserInfoVo checkIfRequestedUserExist(LoginFormVo vo) throws NotFoundException, IllegalArgumentException;
	public boolean insertRefreshTokenIntoDatabase(RefreshTokenWithUseridDto dto);
	public String generateToken(LoginUserInfoVo vo);
	public String generateRefreshToken(LoginUserInfoVo vo);
	public void deleteAuthInfoByUserId(String userid);
}
