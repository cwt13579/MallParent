package com.berchina.service.order;

import java.util.List;
import java.util.Map;

import com.berchina.distribute.frame.annotation.BerService;
import com.berchina.vo.HashMapExt;
import com.iec.core.app.exception.ServiceException;

/**
 * 创建于:2016年7月27日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 订单服务接口，接口中的所有buyerId代表的是买家的唯一标识，不一定是用户表的主键，在不同的业务系统中，可能是手机号码，或者卡号等
 * @author chender
 * @version 1.0
 * 
 */
@BerService(systemTag="order")
public interface IOrderService {
	
	/**
	 * 提交订单
	 * @param order 订单实体
	 * @param userId 用户标识
	 * @return 订单编号
	 * @throws ServiceException 库存不足，商品下架，活动结束等原因也会导致该异常
	 */
	String submitOrder()throws Exception;
	
	/**
	 * 批量提交订单
	 * @param order 订单实体列表
	 * @return 订单编号列表
	 * @throws ServiceException 库存不足，商品下架，活动结束等原因也会导致该异常
	 */
	List<String> submitOrders(HashMapExt hmExt)throws Exception;
	
	/**
	 * 取消订单
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @throws ServiceException 
	 */
	Map<String, String> cancelOrder(String orderId,String buyerId)throws Exception;
	
	/**
	 * 删除订单
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @throws ServiceException 
	 */
	Map<String, String> delOrder(String orderId,String buyerId)throws Exception;
	
	/**
	 * 保存支付流水号，需要通过订单id获取订单金额
	 * @param orderId 订单编号
	 * @param tradId  总交易流水号
	 * @param paySerialNumber  三维易付支付流水号
	 * @throws Exception
	 */
	void savePaySerialNumber(String orderId,String tradId,String paySerialNumber)throws Exception;
	
	/**
	 * 获取支付流水号
	 * @param orderId 订单编号
	 * @param memberId  会员ID
	 * @throws Exception
	 */
	String getPaySerialNumber(String orderId,String memberId)throws Exception;
	
	/**
	 * 三维易付支付成功回调
	 * @param orderId 	订单编号
	 * @param memberId  会员ID
	 * @param orderType 订单类型
	 * @throws Exception
	 */
	Map<String, Object> paySuccessCallback(String orderId,String memberId,String orderType)throws Exception;
	
	/**
	 * 订单支付成功
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @throws ServiceException
	 */
	void paySuccess(String orderId,String buyerId)throws Exception;
	
	/**
	 * 卖家已发货
	 * @param orderId 订单编号
	 * @param sellerId 卖家Id
	 * @throws ServiceException
	 */
	void goodsSended(String orderId,String sellerId)throws Exception;
	
	/**
	 * 确认收货
	 * @param orderId 订单id
	 * @param buyerId 买家id
	 * @throws ServiceException
	 */
	void confirmReceiveGoods(String orderId,String buyerId)throws Exception;
	
	/**
	 * 申请退款（整笔订单）
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @param orderGoodsId 订单中的商品的id集合
	 * @return 退款流水号
	 * @throws ServiceException
	 */
	Map<String, Object> aquireRefund(String orderId,String buyerId)throws Exception;
	
	/**
	 * 申请退款（部分商品）
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @param orderGoodsId 订单中的商品的id集合
	 * @return 退款流水号
	 * @throws ServiceException
	 */
	String aquireRefund(String orderId,String buyerId,List<String> orderGoodsId)throws Exception;
	
	/**
	 * 申请退款（确认收货/商家已发货）
	 * 创建退款记录
	 * @param RefundVo 退款实体
	 * @return map
	 * @throws ServiceException
	 */
	Map<String, Object> applyRefund()throws Exception;
	
	/**
	 * 卖家确认退款
	 * @param refoundNo 退款流水号
	 * @param sellerId 卖家id
	 * @throws ServiceException
	 */
	void confirmRefund(String refoundNo,String sellerId)throws Exception;
	
	/**
	 * 消费完成
	 * @param orderId 订单编号
	 * @param buyerId 买家id
	 * @throws ServiceException
	 */
	void consumeFinish(String orderId,String buyerId)throws Exception;
	
	/**
	 * 获取支付流水号
	 * @param orderId 订单编号
	 * @param memberId  会员ID
	 * @param totalprice 当次支付价格
	 * @throws Exception
	 */
	Map<String,String> getPaySerialNumber(String orderId,String memberId,String totalprice)throws Exception;
	
}

