package com.iec.core.app.middleware.dubbo.client;

import java.util.concurrent.Future;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;

/**
 * 客户端服务调用公共接口
 * 
 * @author SCRCU
 * @version 1.0.0
 */
public interface IServiceInvoker {

	/**
	 * 调用服务（同步）
	 * 
	 * @param request
	 *            服务请求对象
	 * @return 服务响应对象
	 * @throws Exception
	 */
	public ServiceResponse call(ServiceRequest request) throws Exception;

	/**
	 * 调用服务（异步）
	 * 
	 * @param request
	 *            服务请求对象
	 * @return Future对象
	 * @throws Exception
	 */
	public Future<ServiceResponse> asyncCall(ServiceRequest request)
			throws Exception;

}
