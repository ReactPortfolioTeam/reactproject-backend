package com.reactproject.minishop.order.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.reactproject.minishop.order.dto.ItemInformationDto;

import lombok.Data;

@Data
public class ItemVo {

	private int orderId;
	
	@Pattern(regexp = "^[0-9]+$",message="{OnlyNum}")
	@NotNull(message= "{NotNull}")
	private int productId;
	
	@Pattern(regexp = "^[0-9]+$",message="{OnlyNum}")
	@NotNull(message= "{NotNull}")
	private int quantity;
	
	
	public ItemInformationDto toDto() {
		ItemInformationDto dto = new ItemInformationDto();
		return 
			dto.builder()
			.productId(this.productId)
			.quantity(this.quantity)
			.build();
	}
}
