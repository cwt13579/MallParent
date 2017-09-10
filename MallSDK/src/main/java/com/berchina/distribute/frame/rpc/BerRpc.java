package com.berchina.distribute.frame.rpc;

import com.berchina.distribute.frame.base.IDubboCommonService;

/**
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 默认远程调用实现
 * @author chender
 * @version 1.0
 * 
 */
public abstract class BerRpc {
	
	private String type;
	
	public BerRpc(){
		type=initRpcType();
		if(type!=null){
			RpcManager.regRpc(this, true);
		}
	}

	/**
	 * 服务调用接口
	 * @param serviceName 服务名称
	 * @param methodName 方法名称
	 * @param args 参数
	 * @return 服务调用返回值
	 */
	public final Object call(String serviceName,String methodName,Object... args)throws Exception{
		return obtainTranService().call(serviceName, methodName, args);
	}

	public final String obtainRpcType(){
		return type;
	}
	
	/**
	 * 初始化rpc服务类型
	 * @return
	 */
	public abstract String initRpcType();
	
	/**
	 * 获取底层通讯服务
	 * @return
	 */
	public abstract IDubboCommonService obtainTranService();
	
}
