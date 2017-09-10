package com.berchina.distribute.frame.rpc;

import javax.annotation.Resource;

import com.berchina.distribute.frame.annotation.BerInit;
import com.berchina.distribute.frame.base.IDubboCommonService;

/**
 * 创建于:2016年8月5日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 默认的rpc服务
 * @author chender
 * @version 1.0
 * 
 */
@BerInit
public class DefaultBerRpc extends BerRpc {
	
	@Resource
	private IDubboCommonService iDubboCommonService;

	private final String type="default";
	
	public DefaultBerRpc(){
		super();
		RpcManager.setDefaultRpcType(type);
	}
	
	@Override
	public String initRpcType() {
		return type;
	}
	
	@Override
	public IDubboCommonService obtainTranService() {
		return iDubboCommonService;
	}

}
