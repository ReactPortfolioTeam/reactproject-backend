package com.reactproject.minishop.loginAndlogout.service;

import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.reactproject.minishop.loginAndlogout.dao.LoginAndLogoutMapper;
import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;

import lombok.AllArgsConstructor;

@Service
@Primary
@AllArgsConstructor
public class LoginAndLogoutServiceImpl implements LoginAndLogoutService {

	private final LoginAndLogoutMapper mapper;
	
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
}