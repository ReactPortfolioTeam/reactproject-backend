package com.reactproject.minishop.loginAndlogout.service;

import java.util.Calendar;
import java.util.Date;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.http.ResponseEntity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reactproject.minishop.common.enumtype.UserStatus;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;

public class JwtTokenManager {
	private String KEY  = "fsdfadfasdfsdafsadfasdfasd"; //추후 외부로 뺄 예정
	private final Algorithm algorithmHS = Algorithm.HMAC256(KEY);
	
	
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
	
	public boolean decodeJwt(String jwttoken, Date now) throws InvalidAttributesException {
		try {
		    JWTVerifier verifier = JWT.require(algorithmHS)
		        .build(); //Reusable verifier instance
		    	 DecodedJWT jwt = verifier.verify(jwttoken);
		    
		    return jwt.getExpiresAt().compareTo(now)>=1?true:false;
		    
		} catch (JWTVerificationException exception){
			throw new InvalidAttributesException("유효하지 않은 토큰입니다.");
		}
		
	}
	
}
