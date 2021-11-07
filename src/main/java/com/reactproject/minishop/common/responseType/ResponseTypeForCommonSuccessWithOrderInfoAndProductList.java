package com.reactproject.minishop.common.responseType;

import java.util.List;

import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;

import lombok.Data;

@Data
public class ResponseTypeForCommonSuccessWithOrderInfoAndProductList extends ResponseTypeForCommon{

	private OrderInformationDto orderInfo;
	private List<ItemInformationDto> productList; 
}
