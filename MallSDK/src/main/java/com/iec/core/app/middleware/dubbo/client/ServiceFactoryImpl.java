package com.iec.core.app.middleware.dubbo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.iec.core.app.middleware.dubbo.service.IService;

/**
 * Service工厂类实现类
 * 
 * @author SCRCU
 * @version 1.0.0
 */
public class ServiceFactoryImpl implements IServiceFactory,
		ApplicationContextAware {
	private static Logger logger = LoggerFactory
			.getLogger(ServiceFactoryImpl.class);

	private ApplicationContext context;

	/**
	 * 获取远程服务对象
	 *
	 * @param svcId
	 *            远程服务对象id
	 * @return 远程服务对象
	 */
	@Override
	public IService getService(String svcId) {
		logger.debug("Get service by id: " + svcId);
		IService service = (IService) context.getBean(svcId);
		return service;
	}

	/**
	 * 设置spring容器上下文对象
	 * 
	 * @param applicationContext
	 *            spring容器上下文对象
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;

	}

}
