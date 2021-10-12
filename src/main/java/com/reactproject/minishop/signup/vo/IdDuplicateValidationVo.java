package com.reactproject.minishop.signup.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class IdDuplicateValidationVo {
	
	@NotNull(message= "{NotNull}")
	@Length(min=8,message="{Length.id}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{OnlyEngAndNum}")
	private String userid;
}
