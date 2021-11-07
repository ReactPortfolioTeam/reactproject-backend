package com.reactproject.minishop.order.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemInformationDto {

	
	private int orderId;
	private int productId;
	private int quantity;
}
