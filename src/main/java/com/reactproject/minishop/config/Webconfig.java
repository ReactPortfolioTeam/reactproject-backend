package com.reactproject.minishop.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.reactproject.minishop.filter.InvalidTokenCheckFilter;

@Configuration
public class Webconfig implements WebMvcConfigurer  {

	
	private InvalidTokenCheckFilter tokenFilter = new InvalidTokenCheckFilter() ;
	
	@Bean 
	public MessageSource validationMessageSource() { 
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); 
		messageSource.setBasename("classpath:/messages/validation"); 
		messageSource.setDefaultEncoding("UTF-8"); return messageSource; } 
	
	@Override 
	public org.springframework.validation.Validator getValidator() { 
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean(); 
		bean.setValidationMessageSource(validationMessageSource()); 
		return bean; 
		} 
	
	@Bean public FilterRegistrationBean<InvalidTokenCheckFilter> firstFilter(){
		FilterRegistrationBean<InvalidTokenCheckFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(tokenFilter); 
		registrationBean.addUrlPatterns("/api/v1/logout","/api/v1/userinfo/*"); 
		registrationBean.setOrder(1); 
		registrationBean.setName("authFilter"); 
		return registrationBean; 
		}

	}


