package com.reactproject.minishop.loginAndlogout.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.reactproject.minishop.loginAndlogout.vo.LoginFormVo;
import com.reactproject.minishop.loginAndlogout.vo.LoginUserInfoVo;

@Repository
public interface LoginAndLogoutMapper {

	public Optional<LoginUserInfoVo> fetchUserInfoById(String userid);
}
