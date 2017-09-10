
package com.berchina.order.service; 

import com.berchina.common.BerException;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月5日 下午6:01:30 

 * 类说明 

 */

public abstract class OrderProvider {

	public abstract void start()  throws BerException;
	
	/**
	 * 构建买家数据
	 * @return
	 */
	public abstract void buildBuyer()throws BerException;
	
	/**
	 * 构建店铺数据
	 * @throws ServiceException
	 */
	public abstract void buildShops()throws BerException;
	
	/**
	 * 构建商品列表数据
	 * @return
	 * @throws ServiceException
	 */
	public abstract void buildGoods()throws BerException;
	
	/**
	 * 构建订单
	 * @throws ServiceException
	 */
	public abstract void buildOrder()throws BerException;
	
	/**
	 * 保存订单
	 * @throws ServiceException
	 */
	protected abstract void saveOrder()throws BerException;
	
	/**
	 * 结束
	 * @throws ServiceException
	 */
	public abstract void end()throws BerException;
	
	/**
	 * 下单出现异常时进行调用，各实现可以重写该方法，根据自身业务逻辑进行相应的操作
	 */
	public void whenException(){
		
	}
}
 