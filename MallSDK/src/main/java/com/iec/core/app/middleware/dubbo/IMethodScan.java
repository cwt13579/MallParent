package com.iec.core.app.middleware.dubbo;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author Tumq
 *  扫描方法接口
 */
public interface IMethodScan {

	public Map<String, Method> scan(ApplicationContext applicationContext,Class<? extends IDubboService>[] dubboServiceClasses) throws Exception;
}
