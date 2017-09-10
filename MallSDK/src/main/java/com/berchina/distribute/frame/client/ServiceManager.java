package com.berchina.distribute.frame.client;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.berchina.distribute.common.DistributeUtil;
import com.berchina.distribute.frame.annotation.BerService;
import com.berchina.distribute.frame.server.BerServiceProxy;

/**
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 分布式服务管理器
 * @author chender
 * @version 1.0
 * 
 */
public class ServiceManager {

	private static Map<String,Object> services=new HashMap<String, Object>();
	
	private static byte[] lock=new byte[1];
	
	/**
	 * 注册服务
	 * @param serviceClz 服务类(可以是服务的类，也可以是服务的接口的类)
	 * @param service 服务
	 */
	private static final void reg(Class<?> serviceClz,Object service,Object tag){
		services.put(DistributeUtil.buildServiceName(serviceClz, tag), service);
	}
	
	/**
	 * 获取分布式服务
	 * @param clz 服务类
	 * @param tag 服务扩展标识
	 * @return 服务，不存在时返回null
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public static final <T> T aquire(Class<T> clz,Object tag){
		Object obj=services.get(DistributeUtil.buildServiceName(clz, tag));
		if(obj!=null){
			return (T)obj;
		}
		synchronized (lock) {
			obj=services.get(DistributeUtil.buildServiceName(clz, tag));
			if(obj==null){
				BerService bs = clz.getAnnotation(BerService.class);
				if (bs != null&&clz.isInterface()) {
					obj = Proxy.newProxyInstance(clz.getClassLoader(),
							new Class[] { clz }, new BerServiceProxy(clz,tag,bs.systemTag()));
					ServiceManager.reg(clz, obj,tag);
				}
			}
		}
		return (T)obj;
	}
}
