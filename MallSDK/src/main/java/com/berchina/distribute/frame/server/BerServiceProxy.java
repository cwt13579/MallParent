package com.berchina.distribute.frame.server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.berchina.distribute.common.DistributeUtil;
import com.berchina.distribute.common.RpcException;
import com.berchina.distribute.frame.rpc.RpcManager;

/**
 * 
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 分布式服务代理
 * @author chender
 * @version 1.0
 *
 */
public class BerServiceProxy implements InvocationHandler {
	
	private String serviceName;
	private String systemTag;
	private Map<Method,Object> methods=new HashMap<Method, Object>();
	
	/**
	 * 构造方法
	 * @param clz 服务接口类
	 * @param tag 服务实现标识
	 * @param systemTag系统标识
	 */
	public BerServiceProxy(Class<?> clz,Object tag,String systemTag){
		Method[] all=clz.getDeclaredMethods();
		for (Method method : all) {
			int mf=method.getModifiers();
			if(Modifier.isPublic(mf)){
				methods.put(method, 1);
			}
		}
		this.serviceName=DistributeUtil.buildServiceName(clz, tag);
		this.systemTag="".equals(systemTag)?null:systemTag;
	}
	
//	public BerServiceProxy(Class<?> clz,Object target){
//		Method[] all=clz.getDeclaredMethods();
//		/**
//		 * 只远程代理当前服务类中声明的public方法
//		 */
//		for (Method method : all) {
//			int mf=method.getModifiers();
//			if(Modifier.isPublic(mf)){
//				methods.put(method, 1);
//			}
//		}
//		this.serviceName=clz.getName();
//		this.target=target;
//		buildObject=Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
//	} 
	

	@Override
	public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(!methods.containsKey(method)){
			return method.invoke(this, args);
		}else{
			try {
				Object result = RpcManager.getRpc(systemTag).call(serviceName, method.getName(), args);
				if(result!=null && result instanceof RpcException){
					RpcException re=(RpcException)result;
					throw re.getBizException()==null?re:re.getBizException();
				}
				return result;
			} catch (RpcException e) {
				if(e.getBizException()!=null){
					throw e.getBizException();
				}
				throw e;
			}
		}
	}

}
