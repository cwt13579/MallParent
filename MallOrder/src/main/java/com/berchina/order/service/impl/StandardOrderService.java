
package com.berchina.order.service.impl; 

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.berchina.common.BerException;
import com.berchina.distribute.cache.MethodCache;
import com.berchina.distribute.frame.annotation.BerServiceImpl;
import com.berchina.order.dao.IOrderDao;
import com.berchina.order.service.OrderService;
import com.berchina.vo.HashMapExt;
import com.berchina.vo.MemberVo;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月5日 上午11:11:36 

 * 类说明 

 */
@BerServiceImpl(tag="standard")
public class StandardOrderService extends OrderService{

	@PostConstruct
	public void aa(){
		System.out.println("--StandardOrderService start --");
	}
	@Override
	public String submitOrder() throws Exception {
		MemberVo member = berOrderService.findMemberById("121sdaDS");
		return member.toString();
	}

	@Override
	public List<String> submitOrders(HashMapExt hmExt) throws Exception {
		System.out.println("自有属性："+hmExt.getPageIndex());
		System.out.println("自有属性："+hmExt.getPageSize());
		System.out.println("map键值对："+hmExt.get("name"));
		berOrderService.saveMember();
		return null;
	}

	@Override
	public Map<String, String> delOrder(String orderId, String buyerId)
			throws BerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void savePaySerialNumber(String orderId, String tradId,
			String paySerialNumber) throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPaySerialNumber(String orderId, String memberId)
			throws BerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> paySuccessCallback(String orderId,
			String memberId, String orderType) throws BerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paySuccess(String orderId, String buyerId) throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goodsSended(String orderId, String sellerId)
			throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmReceiveGoods(String orderId, String buyerId)
			throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> aquireRefund(String orderId, String buyerId)
			throws BerException {
		// TODO Auto-generated method stub
		return null;
	}
 

	@Override
	public Map<String, Object> applyRefund() throws BerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void confirmRefund(String refoundNo, String sellerId)
			throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consumeFinish(String orderId, String buyerId)
			throws BerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getPaySerialNumber(String orderId,
			String memberId, String totalprice) throws BerException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String aquireRefund(String orderId, String buyerId,	List<String> orderGoodsId) throws Exception {
		 
		return null;
	}

}
 