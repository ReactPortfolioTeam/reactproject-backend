package com.reactproject.minishop.loginAndlogout.service;

import java.util.Optional;

import javax.servlet.http.Cookie;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.common.enumtype.UserStatus;
import com.reactproject.minishop.loginAndlogout.dao.LoginAndLogoutMapper;
import com.reactproject.minishop.loginAndlogout.dto.RefreshTokenWithUseridDto;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;
import com.reactproject.minishop.loginAndlogout.vo.RefreshToken;

import lombok.AllArgsConstructor;

@Service
@Primary
@AllArgsConstructor
public class LoginAndLogoutServiceImpl implements LoginAndLogoutService {

	private final ObjectMapper converter;
	private final LoginAndLogoutMapper mapper;
	private final JwtTokenManager manager;
	
	@Override
	public LoginUserInfoVo checkIfRequestedUserExist(LoginFormVo vo) throws NotFoundException,IllegalArgumentException {
		
		Optional<LoginUserInfoVo> userInfo = mapper.fetchUserInfoById(vo.getUserid());
		
		if(userInfo.isEmpty()) {
			throw new NotFoundException("존재하지 않는 사용자입니다.");
		}
		
		if(!vo.getPassword().equals(userInfo.get().getPassword())) {
			throw new IllegalArgumentException("비밀번호를 확인해주세요");
		}
		
		return userInfo.get();
	}
	
	@Override
	public boolean insertRefreshTokenIntoDatabase(RefreshTokenWithUseridDto dto) {
		try {
			
			mapper.insertRefreshToken(dto);
			
			return true;
		
		}catch(Exception e) {
			e.fillInStackTrace();
		}
			return false;
	}
	
	@Override
	public String generateRefreshToken(LoginUserInfoVo vo) {
		// TODO Auto-generated method stub
		return manager.generateJwtRefreshStringTokenWith(vo.getUserid());
	}
	
	@Override
	public String generateToken(LoginUserInfoVo vo) {
		// TODO Auto-generated method stub
		return manager.generateJwtStringWith(vo.getUserid(),UserStatus.MEMBER);
	}
	
	@Override
	public void deleteAuthInfoByUserId(String userid) {
		mapper.deleteRefreshTokenWithUserId(userid);
		
	}
	
	@Override
	public String getUserIdFromJwtToken(String token) {
		Claim userid = JwtTokenManager.generateDecode(token).getClaim("userid");
		return userid.asString();
	}
	
	@Override
	public String checkIfRefreshTokenIsValid(RefreshToken token) throws IllegalArgumentException {
		RefreshToken tokenInfo=  mapper.fetchRefreshToken(token.getUserId());
		if(tokenInfo != null) {
			
	
			
			System.out.println(token.toString());
			LoginUserInfoVo vo = mapper.fetchUserInfoById(tokenInfo.getUserId()).get();
			String newToken = manager.generateJwtStringWith(tokenInfo.getUserId(), UserStatus.valueOf(vo.getLevel().toUpperCase()));
			
			return newToken;
		}
		
		return null;
	}
	 
	@Override
	public Cookie createCookieWithRefreshToken(String refreshToken) {
		Cookie refreshCookie = new Cookie("refreshToken",refreshToken);
		refreshCookie.setPath("/");
		refreshCookie.setHttpOnly(true);
		refreshCookie.setMaxAge(60*24);
		return refreshCookie;
		
	}

}
