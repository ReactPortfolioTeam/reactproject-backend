package com.reactproject.minishop.order.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;

@Mapper
@Repository
public interface OrderMapper {

	public void insertOrderInformation(OrderInformationDto orderDto);

	public void insertOrderedProductList(ItemInformationDto vo);

	public OrderInformationDto getOrderInfoByUserIdAndOrderId(String userid, String orderid);

	public List<ItemInformationDto> getOrderedItemsByUserIdAndOrderId(String userid); 
	
	
}
