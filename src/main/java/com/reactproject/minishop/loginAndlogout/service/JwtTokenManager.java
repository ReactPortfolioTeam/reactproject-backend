package com.reactproject.minishop.loginAndlogout.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reactproject.minishop.common.enumtype.UserStatus;


/*
 * 작성자 : 조현일
 * JWT 토큰 생성 관리 클래스
 * KEY는 추후 외부로 뺄 예정
 * 토큰 스팩 
 * 
 * accessToken = userid, status, expiretime
 * refreshToken = userid, expiretime
 * Token 디코드 시, 토큰과 현재시간을 함께 제시하여, 1이상의 숫자가 나오면 유효
 * */

@Service
public class JwtTokenManager {
	private static String KEY  = "fsdfadfasdfsdafsadfasdfasd"; //추후 외부로 뺄 예정
	private final static Algorithm algorithmHS = Algorithm.HMAC256(KEY);
	
	
	public String getKey() {
		return KEY;
	}
	
	public String generateJwtStringWith(String userid, UserStatus status) {
		
		Date forNow = new Date();
		Calendar in30minutes = Calendar.getInstance();
		in30minutes.setTime(forNow);
		in30minutes.add(Calendar.MINUTE, 30);
		
		String token = "";
		
		try {
			token = JWT.create()
		        .withClaim("userid", userid)
		        .withClaim("status", status.toString())
		        .withExpiresAt(in30minutes.getTime())
		        .sign(algorithmHS);
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		
		return token;
	}
	
	public String generateJwtRefreshStringTokenWith(String userid) {
			
			Date forNow = new Date();
			Calendar in9Hour = Calendar.getInstance();
			in9Hour.setTime(forNow);
			in9Hour.add(Calendar.HOUR, 9);
			
			String token = "";
			
			try {
				token = JWT.create()
			        .withClaim("userid", userid)
			        .withExpiresAt(in9Hour.getTime())
			        .sign(algorithmHS);
			} catch (JWTCreationException exception){
			    //Invalid Signing configuration / Couldn't convert Claims.
			}
			
			return token;
		}
	
	
	
	public static boolean verifyToken(String token) throws IllegalArgumentException, JWTDecodeException {
	
			try {
		    DecodedJWT jwt = generateDecode(token);
			}catch(TokenExpiredException e) {
				throw new IllegalArgumentException("유효기간이 지난 토큰입니다");
			}catch(JWTVerificationException e) {
				throw new IllegalArgumentException("유효하지 않은 토큰입니다");
			}
		    
		    return true;
	}
	
	private static DecodedJWT generateDecode(String jwttoken) {
		JWTVerifier verifier = JWT.require(algorithmHS)
		    .build(); //Reusable verifier instance
			 DecodedJWT jwt = verifier.verify(jwttoken);
		return jwt;
	}
	
}
