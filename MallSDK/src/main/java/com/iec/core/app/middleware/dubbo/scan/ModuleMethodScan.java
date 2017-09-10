package com.iec.core.app.middleware.dubbo.scan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.dubbo.config.annotation.Service;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.exception.ServiceConflictException;
import com.iec.core.app.middleware.RequestMapping;
import com.iec.core.app.middleware.dubbo.IDubboService;
import com.iec.core.app.middleware.dubbo.IMethodScan;

/**
 * @author tumq
 * @Date 2015年1月9日
 * @describe 模块方法扫描
 */
public class ModuleMethodScan implements IMethodScan {

	private static final Logger log = LoggerFactory
			.getLogger(ModuleMethodScan.class);
	private final static Map<DubboServiceKey,String> dubboServiceKeySet = new HashMap<DubboServiceKey, String>();

	@Override
	public Map<String, Method> scan(ApplicationContext applicationContext,
			Class<? extends IDubboService>[] dubboServiceClasses)
			throws IOException {
		Map<String, Method> methodMap = new HashMap<String, Method>();
		ArrayList<Method> methods = new ArrayList<Method>();
		/* 判断service是否冲突 start */
		for (Class<? extends IDubboService> dubboServiceClass : dubboServiceClasses) {
			Service serviceAnno = dubboServiceClass.getAnnotation(Service.class);
			if (null != serviceAnno) {
				for (Class interfacee : dubboServiceClass.getInterfaces()) {
					DubboServiceKey serviceKey = new DubboServiceKey(interfacee.getName(), serviceAnno.group());
					synchronized (dubboServiceKeySet) {
						if (dubboServiceKeySet.containsKey(serviceKey)) {
							log.warn(serviceKey.getInterfaceName()+"---->Key:-->"+dubboServiceKeySet.get(serviceKey)+"---has exist!");
							throw new ServiceConflictException(dubboServiceClass.getName(),dubboServiceKeySet.get(serviceKey), serviceKey + " has exist!");
						} else {
							dubboServiceKeySet.put(serviceKey,dubboServiceClass.getName());
						}
					}
				}
			}
			/* 判断service是否冲突 end */
			methods.addAll(Arrays.asList(dubboServiceClass.getDeclaredMethods()));
		}
		/* 判断service是否冲突 end */
		RequestMapping service = null;
		for (Method method : methods) {
			
			service = method.getAnnotation(RequestMapping.class);

			if (null == service)
				continue;
			
			Class[] paraTypes = method.getParameterTypes();
			if(paraTypes.length != 1){
				throw new ConversionException(method.getName()+" method parameter number isn't 1");
			}
			
			String serviceName = service.id();

			if (methodMap.containsKey(serviceName)) {
				String message = this.getClass().getName()+ " error: serviceName conflict," + serviceName;
				log.warn(message);
				throw new IOException(message);
			}

			log.info(serviceName + ">>>>>" + method.getName());

			methodMap.put(serviceName, method);
		}
		return methodMap;
	}

}
