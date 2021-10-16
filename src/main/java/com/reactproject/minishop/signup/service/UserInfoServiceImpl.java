package com.reactproject.minishop.signup.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.reactproject.minishop.signup.dao.UserManageMapper;
import com.reactproject.minishop.signup.dto.UserSignupInfoDto;
import com.reactproject.minishop.signup.vo.UserInfoChangeVo;

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
	
	@Override
	public void updateUserInfo(UserInfoChangeVo vo) {
		mapper.updateUserInfo(vo);
	}
	
	@Override
	public boolean checkIfUserExist(String userid) {
		String user = mapper.findById(userid);
		
		return user==null?false:true;
	}
}
