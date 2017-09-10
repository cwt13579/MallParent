package com.iec.core.app.middleware.dubbo.conversion;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.IParaConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;

public class RequestAndObjectConversion<Q> implements IParaConversion<ServiceMethodRequest,Q>{
	
	private static final String OBJECT = "single_object";

	@Override
	public ServiceRequest ObjectToServiceRequest(ServiceMethodRequest request)
			throws ConversionException {
		return request;
	}

	@Override
	public Q serviceResponseToObject(ServiceResponse response,Type type)
			throws ConversionException {
		return (Q)response.getBodyValue(OBJECT);
	}

	@Override
	public ServiceMethodRequest serviceRequestToObject(ServiceRequest request,Method method)
			throws ConversionException {
		return (ServiceMethodRequest) request;
	}

	@Override
	public ServiceResponse objectToServiceResponse(ServiceRequest request,
			Q response,Method method) throws ConversionException {
		ServiceResponse serviceResponse = ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.retInfo(request);
		serviceResponse.setBodyValue(OBJECT, response);
		return serviceResponse;
	}



}
