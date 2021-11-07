package com.reactproject.minishop.order.service;

import java.sql.SQLException;
import java.util.List;

import com.reactproject.minishop.common.AbstractGlobalInputErrorInterface;
import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;

public interface OrderService extends AbstractGlobalInputErrorInterface {

	int insertOrderInformation(OrderInformationDto orderDto) throws SQLException;

	void insertProductList(List<ItemInformationDto> list);

	OrderInformationDto getOrderInfoByUseridAndOrderId(String userid,String orderid);

	List<ItemInformationDto> getOrderedItemsByUserIdAndOrderId(String userid, String orderid);

}
