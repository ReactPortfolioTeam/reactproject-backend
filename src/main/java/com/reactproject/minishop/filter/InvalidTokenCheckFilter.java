package com.reactproject.minishop.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;
import com.reactproject.minishop.loginAndlogout.service.JwtTokenManager;


public class InvalidTokenCheckFilter extends OncePerRequestFilter  {

	private ObjectMapper mapper= new ObjectMapper();
	private SimpleDateFormat format;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authHeader = httpServletRequest.getHeader("Authorization");
		System.out.println(authHeader);
		
		try {
			
			if(authHeader==""||authHeader==null) {
				throw new NullPointerException("로그인이 필요한 서비스입니다");
			}
			
			JwtTokenManager.verifyToken(authHeader);
			filterChain.doFilter(httpServletRequest, response);
			
		}catch(NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		
			
			setErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(),(HttpServletResponse) response);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류가 발생했습니다",(HttpServletResponse) response);
		}  		
	}
	
	public void setErrorResponse(HttpStatus status,String ex,HttpServletResponse response){
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseTypeForCommonErrorWithOnlyAMsg errorResponse = new ResponseTypeForCommonErrorWithOnlyAMsg();
        
   
        errorResponse.setIssuedAt(new Date());
        errorResponse.setStatusCode(401);
        errorResponse.setMsg(ex);
        
        
        try{
            String json = mapper.writeValueAsString(errorResponse);
            System.out.println("jsonResult "+json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
