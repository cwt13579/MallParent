package com.iec.core.app.middleware.dubbo.client;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.gson.reflect.TypeToken;
import com.iec.core.app.middleware.dubbo.service.IService;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ServiceException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.IClientConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;
import com.iec.core.app.middleware.dubbo.ServiceMethodResponse;

/**
 * @author tumq
 * @Date 2015年1月9日
 * @describe
 */
public class DubboDirectClient extends AbstractClient {
    private static Logger logger= LoggerFactory.getLogger(DubboDirectClient.class);
	private IService service;

	public IService getService() {
		return service;
	}

	public void setService(IService service) {
		this.service = service;
	}


	@Override
	public ServiceMethodResponse send(ServiceRequest request)
			throws ServiceException {
		ServiceResponse serviceResponse = null;
		try {
		    setRequestCommon(request);
		    if(request instanceof ServiceMethodRequest){
		        serviceResponse = service.invoke(((ServiceMethodRequest) request).copyToServiceRequest());
		    }else{
		        serviceResponse = service.invoke(request);
		    }
		} catch (Exception e) {
			logger.error("*****APP系统异常*****", e);
			throw new ServiceException(e, "EBCP999999");
		}
		if (serviceResponse.getRespCode() != ResponseInfoEnum.RECIPROCAL_REQ_SUCESS
				.getCode()) {
			throw new ServiceException(serviceResponse.getRespMsg(),
					serviceResponse.getRespCode());
		}
		ServiceMethodResponse response = new ServiceMethodResponse();
        response.getHeadMap().putAll(serviceResponse.getHeadMap());
        response.getBodyMap().putAll(serviceResponse.getBodyMap());
		return response;
	}

	@Override
	public <Q> Q sendByConversion(ServiceRequest request,
			IClientConversion<ServiceRequest, Q> clientConversionClass)
			throws ServiceException {
		
		ServiceResponse serviceResponse = null;
		try {
			serviceResponse = send(request);
		} catch (Exception e) {
			logger.error("*****APP系统异常*****", e);
			throw new ServiceException(e, "EBCP999999");
		}
		if (serviceResponse.getRespCode() != ResponseInfoEnum.RECIPROCAL_REQ_SUCESS
				.getCode()) {
			throw new ServiceException(serviceResponse.getRespMsg(),
					serviceResponse.getRespCode());
		}
		final TypeToken<Q> typeToken = new TypeToken<Q>(){};
		return clientConversionClass.serviceResponseToObject(serviceResponse,typeToken.getType());
	}

	@Override
	public Future<ServiceMethodResponse> asyncSend(final ServiceRequest request) {
		return RpcContext.getContext().asyncCall(new Callable<ServiceMethodResponse>() {
			@Override
			public ServiceMethodResponse call() throws Exception {
				return DubboDirectClient.this.send(request);
			}
		});
	}

	@Override
	public <Q> Future<Q> asyncSendByConversion(final ServiceRequest request,
			final IClientConversion<ServiceRequest, Q> clientConversionClass) {
		return RpcContext.getContext().asyncCall(new Callable<Q>() {
			@Override
			public Q call() throws Exception {
				return DubboDirectClient.this.sendByConversion(request,clientConversionClass);
			}
		});
	}

}
