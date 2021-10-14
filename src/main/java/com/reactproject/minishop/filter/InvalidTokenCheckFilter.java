package com.reactproject.minishop.filter;

import java.io.IOException;
import java.util.Date;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.reactproject.minishop.loginAndlogout.service.JwtTokenManager;


public class InvalidTokenCheckFilter implements Filter {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("gi");
		
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authHeader = httpServletRequest.getHeader("Authorization");
		System.out.println(authHeader);
		
		
		try {
			
			if(authHeader.isEmpty()||authHeader==null) {
				throw new NullPointerException();
			}
			
			JwtTokenManager.verifyToken(authHeader);
			System.out.println("토큰키일치");
			System.out.println("날짜ㅇㅋ");
			chain.doFilter(httpServletRequest, response);
			
		}catch(NullPointerException e) {
			throw new IllegalArgumentException("토큰을 입력해주세요");
		
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}  
	}

	
}
