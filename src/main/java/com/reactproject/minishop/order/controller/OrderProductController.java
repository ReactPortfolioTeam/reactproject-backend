package com.reactproject.minishop.order.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactproject.minishop.common.responseType.ResponseTypeForCommonError;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonErrorWithOnlyAMsg;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccessWithOrderId;
import com.reactproject.minishop.common.responseType.ResponseTypeForCommonSuccessWithOrderInfoAndProductList;
import com.reactproject.minishop.order.dto.ItemInformationDto;
import com.reactproject.minishop.order.dto.OrderInformationDto;
import com.reactproject.minishop.order.service.OrderService;
import com.reactproject.minishop.order.vo.OrderInfomationVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin("*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/api/v1/order")
public class OrderProductController {
	
	private final OrderService service;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> getOrderInformation(@Validated @RequestBody OrderInfomationVo vo, BindingResult error) {
		
		if(error.hasErrors()) {
			
			ResponseTypeForCommonError errorMsg = service.extractErrorMsgFromErrorObject(error);
		    return new ResponseEntity<ResponseTypeForCommonError>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		
		//1. ??????????????? ????????????????????? ????????????. 
		
		try {
			int orderId = service.insertOrderInformation(vo.toOrderDto());
			List<ItemInformationDto> list = vo.toDto();
			list.forEach(a->a.setOrderId(orderId));
			
			System.out.println("??? ????????? ?????????? : "+vo.getOrderPrice());
			
			service.insertProductList(list);
			ResponseTypeForCommonSuccessWithOrderId msg = new ResponseTypeForCommonSuccessWithOrderId();
			msg.setIssuedAt(new Date());
			msg.setMsg("????????? ?????????????????????");
			msg.setStatusCode(201);
			msg.setOrderId(orderId);
			return new ResponseEntity<ResponseTypeForCommonSuccessWithOrderId>(msg,HttpStatus.ACCEPTED);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
			msg.setIssuedAt(new Date());
			msg.setMsg(e.getMessage());
			msg.setStatusCode(400);
		    return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg, HttpStatus.BAD_REQUEST);

			
		}
		
	}
	
	@GetMapping("/{userid}/{orderid}")
	public ResponseEntity<?> getOrderHistory(@PathVariable String userid, @PathVariable String orderid) {
		
		
		//1. ?????? ???????????? ?????? ???????????? ?????????, ????????? ?????? ????????? ????????? ????????????
		//2. ?????? ????????? ???????????? ?????? ??? 404 ?????? ???????????? ?????????
		//3. ?????? ????????? ????????? ???, ???????????? ????????????.
		
		OrderInformationDto dto = service.getOrderInfoByUseridAndOrderId(userid,orderid);
		List<ItemInformationDto> list = service.getOrderedItemsByUserIdAndOrderId(userid,orderid);
		
		if(dto == null || list == null) {
			ResponseTypeForCommonErrorWithOnlyAMsg msg = new ResponseTypeForCommonErrorWithOnlyAMsg();
			msg.setIssuedAt(new Date());
			msg.setMsg("???????????? ?????? ?????????????????????");
			msg.setStatusCode(404);
			return new ResponseEntity<ResponseTypeForCommonErrorWithOnlyAMsg>(msg,HttpStatus.NOT_FOUND);
		}
		
		ResponseTypeForCommonSuccessWithOrderInfoAndProductList msg = new ResponseTypeForCommonSuccessWithOrderInfoAndProductList();
		msg.setIssuedAt(new Date());
		msg.setStatusCode(200);
		msg.setOrderInfo(dto);
		msg.setProductList(list);
		
		
		return new ResponseEntity<ResponseTypeForCommonSuccessWithOrderInfoAndProductList>(msg,HttpStatus.ACCEPTED);
	}



}

