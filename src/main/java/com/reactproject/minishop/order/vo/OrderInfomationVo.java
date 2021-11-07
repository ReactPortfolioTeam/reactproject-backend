package com.reactproject.minishop.order.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;

import lombok.Data;

@Data
public class OrderInfomationVo {

	private int orderId;
	@NotEmpty(message="{Empty}")
	@NotNull(message= "{NotNull}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message="{OnlyEngAndNum}")
	private String userId;
	
	@NotEmpty(message="{Empty}")
	@NotNull(message= "{NotNull}")
	@Pattern(regexp = "^[0-9]+$",message="{OnlyNum}")
	@Length(min=6,message="{Length.phone}")
	private String phoneNumber;
	
	@NotEmpty(message="{Empty}")
	@NotNull(message= "{NotNull}")
	private String shipmentAddress;
	

	private String orderState;
	
	
	private Date orderDate;
	
	@NotEmpty(message="{Empty}")
	@NotNull(message= "{NotNull}")
	@Pattern(regexp = "^[0-9]+$",message="{OnlyNum}")
	private String orderPrice;
	
	private List<ItemVo> list;
	
	public OrderInformationDto toOrderDto() {
		OrderInformationDto dto = new OrderInformationDto();
		return dto.builder()
			.userId(this.userId)
			.phoneNumber(this.phoneNumber)
			.shipmentAddress(this.shipmentAddress)
			.orderDate(new Date())
			.orderPrice(this.orderPrice)
			.build();
	}
	
	public List<ItemInformationDto> toDto(){
		List<ItemInformationDto> list = new ArrayList<>();
		this.list.forEach(a->list.add(a.toDto()));
		return list;

	}
 }
