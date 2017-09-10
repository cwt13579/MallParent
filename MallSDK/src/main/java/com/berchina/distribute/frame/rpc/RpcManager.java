package com.berchina.distribute.frame.rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 远程调用管理器
 * @author chender
 * @version 1.0
 * 
 */
public class RpcManager {
		
	private static final Map<String,BerRpc> rpcs=new HashMap<String, BerRpc>();
	private static String defaultRpcType;
	
	private RpcManager(){
		
	}
	
	/**
	 * 设置默认的rpc类型，默认的rpc实现必须放在和和RpcManager相同的包内
	 * @param type rpc类型
	 */
	static final void setDefaultRpcType(String type){
		defaultRpcType=type;
	}
	
	/**
	 * 注册rpc服务
	 * @param type rpc类型
	 * @param rpc
	 * @param cover
	 */
	public static final void regRpc(BerRpc rpc,boolean cover){
		String type=rpc.obtainRpcType();
		if(type.equals(defaultRpcType)){
			throw new RuntimeException("暂时不允许替换默认的rpc类型:"+type);
		}
		if(!cover&&rpcs.containsKey(type)){
			throw new RuntimeException("注册rpc服务失败，重复的rpc调用类型:"+type);
		}
		rpcs.put(type, rpc);
	}
	public static final BerRpc getRpc(String type){
		if(type==null){
			return getDefaultRpc();
		}
		BerRpc b= rpcs.get(type);
		return b;
	}
	
	public static final BerRpc getDefaultRpc(){
		return  rpcs.get(defaultRpcType);
	}
	
	public static final Object defaultCall(String serviceName,String methodName,Object... args) throws Exception{
		return getDefaultRpc().call(serviceName, methodName, args);
	}
}
