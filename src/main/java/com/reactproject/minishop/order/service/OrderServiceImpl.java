package com.reactproject.minishop.order.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.reactproject.minishop.order.dao.OrderMapper;
import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@Slf4j
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderMapper mapper;
	
	@Override
	public int insertOrderInformation(OrderInformationDto orderDto) throws SQLException {
		mapper.insertOrderInformation(orderDto);

		
		return orderDto.getOrderId();
	}

	@Override
	public void insertProductList(List<ItemInformationDto> list) {
		list.forEach(a->mapper.insertOrderedProductList(a));
		
	}
	
	@Override
	public List<ItemInformationDto> getOrderedItemsByUserIdAndOrderId(String userid, String orderid) {
		return mapper.getOrderedItemsByUserIdAndOrderId(orderid);
	}
	
	@Override
	public OrderInformationDto getOrderInfoByUseridAndOrderId(String userid, String orderid) {
		// TODO Auto-generated method stub
		return mapper.getOrderInfoByUserIdAndOrderId(userid,orderid);
	}
	
}
