package com.berchina.order.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.berchina.common.BerException;
import com.berchina.order.service.common.BerOrderService;
import com.berchina.service.order.IOrderService;


/**
 * 创建于:2016年7月27日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 订单接口实现类
 * @author chender
 * @version 1.0
 * 
 */
public abstract class OrderService implements IOrderService {
	
	private static final Logger log = Logger.getLogger(OrderService.class);
	
	@Autowired
	protected BerOrderService berOrderService;
	
	/**
	 * 取消订单
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @throws BerException 
	 */
	@Override
	public Map<String, String> cancelOrder(String orderId, String buyerId) throws BerException {
		return null;
	}
}
