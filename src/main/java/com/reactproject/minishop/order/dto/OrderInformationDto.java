package com.reactproject.minishop.order.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderInformationDto {

	public OrderInformationDto() {
		// TODO Auto-generated constructor stub
	}
	
	private int orderId;
	private String userId;
	private String phoneNumber;
	private String shipmentAddress;
	private String orderState;
	private Date orderDate;
	private String orderPrice;
	
}
