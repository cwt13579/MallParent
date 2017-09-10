package com.berchina.web.rpc;

import javax.annotation.Resource;

import com.berchina.distribute.frame.annotation.BerInit;
import com.berchina.distribute.frame.base.IDubboCommonService;
import com.berchina.distribute.frame.rpc.BerRpc;

/**
 * 创建于:2016年8月5日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 订单系统rpc实现
 * @author chender
 * @version 1.0
 * 
 */
@BerInit
public class OrderBerRpc extends BerRpc {

	@Resource
	private IDubboCommonService iDubboCommonService_order;
	
	@Override
	public String initRpcType() {
		return "order";
	}

	@Override
	public IDubboCommonService obtainTranService() {
		return iDubboCommonService_order;
	}

}
