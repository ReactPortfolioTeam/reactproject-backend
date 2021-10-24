package com.reactproject.minishop.loginAndlogout.service;

import javax.servlet.http.Cookie;

import org.apache.ibatis.javassist.NotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.reactproject.minishop.common.AbstractGlobalInputErrorInterface;
import com.reactproject.minishop.loginAndlogout.dto.RefreshTokenWithUseridDto;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;
import com.reactproject.minishop.loginAndlogout.vo.RefreshToken;

public interface LoginAndLogoutService extends AbstractGlobalInputErrorInterface {

	public LoginUserInfoVo checkIfRequestedUserExist(LoginFormVo vo) throws NotFoundException, IllegalArgumentException;
	public boolean insertRefreshTokenIntoDatabase(RefreshTokenWithUseridDto dto);
	public String generateToken(LoginUserInfoVo vo);
	public String generateRefreshToken(LoginUserInfoVo vo);
	public void deleteAuthInfoByUserId(String userid);
	public String getUserIdFromJwtToken(String token);
	public String checkIfRefreshTokenIsValid(RefreshToken token) throws IllegalArgumentException;
	public Cookie createCookieWithRefreshToken(String refreshToken);
}
