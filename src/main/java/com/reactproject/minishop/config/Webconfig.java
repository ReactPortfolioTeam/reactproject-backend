package com.reactproject.minishop.config;

import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer  {

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
	}


