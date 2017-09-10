package com.berchina.distribute.frame.base.impl;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.berchina.distribute.common.RpcException;
import com.berchina.distribute.frame.base.IDubboCommonService;
import com.berchina.distribute.frame.server.ServiceImplManager;

public abstract class DubboCommonService  implements IDubboCommonService {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	private Field cause;
	
	public DubboCommonService(){
		try {
			cause=Throwable.class.getDeclaredField("cause");
			cause.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException("初始化环境失败",e);
		}
	
	}

	protected final Object doCall(String serviceName, String methodName, Object... params) throws RpcException {
		Object obj=ServiceImplManager.aquire(serviceName);
		if(obj==null){
			throw new RpcException("service-not-exist","服务不存在:"+serviceName);
		}
		Method method=ServiceImplManager.aquire(serviceName, methodName,params);
		if(method==null){
			throw new RpcException("method-not-found", "找不到可访问的服务方法:"+serviceName+"."+methodName);
		}
		try {
			return method.invoke(obj, params);
		} catch (Exception e) {
			String msg="调用服务出现异常"+serviceName+"."+methodName;
			logger.error(msg,e);
			Throwable t=e;
			if(e instanceof InvocationTargetException){
				t=((InvocationTargetException)e).getTargetException();
				try {
					cause.set(t, null);//dubbo的默认序列化协议中，所有的对象必须有无参构造方法，所有将cause设置为空，以免序列化失败
				} catch (Exception e1) {
					t=new RpcException("biz-service-exception","服务异常");
				}
			}
			throw new RpcException("biz-service-exception",msg,t);
		}
	
	}

}
