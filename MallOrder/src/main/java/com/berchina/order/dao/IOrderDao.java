package com.berchina.order.dao;

import java.util.List;

import com.berchina.vo.GoodsVo;
import com.berchina.vo.MemberVo;
import com.berchina.vo.OrderGoodsVo;
import com.berchina.vo.OrderVo;
import com.berchina.vo.ShopVo;


/**
 * 
 * @ClassName:     IOrderDao.java
 * @Description:   订单相关持久化服务
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午11:07:50
 */
public interface IOrderDao {
	
	public List<MemberVo> findMemberBy() throws Exception;

	public MemberVo findMemberById(String memberId) throws Exception;
	
	public OrderVo findOrderById(String orderId) throws Exception;
	
	public ShopVo findShopById(String shopId) throws Exception;
	
	public GoodsVo findGoodsById(String goodsId) throws Exception;
	
	public List<OrderGoodsVo> findOrderGoodsByOrderId(String orderId) throws Exception;
	
	public void saveMember(MemberVo member)throws Exception;
}
