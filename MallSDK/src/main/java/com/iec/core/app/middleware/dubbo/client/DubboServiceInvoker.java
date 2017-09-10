package com.iec.core.app.middleware.dubbo.client;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcContext;
import com.iec.core.app.middleware.dubbo.service.IService;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;

/**
 * DUBBO框架下的客户端服务调用工具类 <br>
 * 提供同步和异步两种调用方式 <br>
 * 需要在DUBBO客户端配置中指定async="true"并设置timeout值来支持异步
 * 
 * @author SCRCU
 * 
 */
public class DubboServiceInvoker implements IServiceInvoker {

	private static Logger logger = LoggerFactory
			.getLogger(DubboServiceInvoker.class);
	private IServiceFactory serviceFactory;

	public IServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	public void setServiceFactory(IServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * 调用服务（同步）
	 * 
	 * @param request
	 *            服务请求对象
	 * @return 服务响应对象
	 * 
	 * @throws IllegalArgumentException
	 *             if service id in the request is empty
	 * @throws NullPointerException
	 *             if request is null or service id in the request is null
	 */
	public ServiceResponse call(ServiceRequest request) throws Exception {
		// TODO 后期会加入数据项检查工具类，现在暂时不提取方法
		logger.debug("New request called.");
		if (request == null) {
			throw new IllegalArgumentException("Request");
		} else if (request.getServiceId() == null) {
			throw new NullPointerException("Service ID");
		} else if ("".equals(request.getServiceId().trim())) {
			throw new IllegalArgumentException("Service ID");
		}
		logger.info("Start calling service synchronously. Service: "
				+ request.getServiceId() + ", Sequence No.: "
				+ request.getReqSeqNo() + ", Business No.: "
				+ request.getBizTrackNo() + ", Head: "
				+ request.getHeadMap().toString());
		IService service = serviceFactory.getService(request.getServiceId());

		return service.invoke(request);

	}

	/**
	 * 调用服务（异步）<br>
	 * 客户端在调用后需判断是否执行完成再获取response对象，否则返回null
	 * 
	 * <pre>
	 * if (future.isDone()) {
	 * 	res = future.get();
	 * }
	 * </pre>
	 * 
	 * @param request
	 *            服务请求对象
	 * @return 可返回ServiceResponse的Future对象
	 * 
	 * @throws IllegalArgumentException
	 *             if service id in the request is empty
	 * @throws NullPointerException
	 *             if request is null or service id in the request is null
	 */
	public Future<ServiceResponse> asyncCall(ServiceRequest request)
			throws Exception {

		if (request == null) {
			throw new IllegalArgumentException("Request");
		} else if (request.getServiceId() == null) {
			throw new NullPointerException("Service ID");
		} else if ("".equals(request.getServiceId().trim())) {
			throw new IllegalArgumentException("Service ID");
		}

		// TODO 对于异步调用出现异常时向客户端返回异常信息的问题 还有待解决。
		logger.info("Start calling service asynchronously. Service: "
				+ request.getServiceId() + ", Sequence No.: "
				+ request.getReqSeqNo() + ", Business No.: "
				+ request.getBizTrackNo() + ", Head: "
				+ request.getHeadMap().toString());
		serviceFactory.getService(request.getServiceId() + "_A").invoke(request);

		return RpcContext.getContext().getFuture();

	}

}
