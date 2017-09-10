package com.iec.core.app.middleware.dubbo.conversion;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.middleware.dubbo.IParaConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;

/**
 * @author tumq
 * @Date 2015年1月9日
 * @describe 原参数转换器
 */
public class OriginalParaConversion implements IParaConversion<ServiceMethodRequest,ServiceResponse>{

	@Override
	public ServiceMethodRequest serviceRequestToObject(ServiceRequest request,Method method) {
		return (ServiceMethodRequest)request;
	}

	@Override
	public ServiceRequest ObjectToServiceRequest(ServiceMethodRequest request) {
		return request;
	}

	@Override
	public ServiceResponse serviceResponseToObject(ServiceResponse response,Type type) {
		return response;
	}

	@Override
	public ServiceResponse objectToServiceResponse(ServiceRequest request,ServiceResponse response,Method method) {
		return response;
	}

}
