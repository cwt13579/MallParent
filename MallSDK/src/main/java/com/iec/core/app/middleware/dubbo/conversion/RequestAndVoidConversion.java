package com.iec.core.app.middleware.dubbo.conversion;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.IParaConversion;

public class RequestAndVoidConversion implements IParaConversion<ServiceRequest,Void>{

	@Override
	public ServiceRequest serviceRequestToObject(ServiceRequest request,Method method) {
		return request;
	}

	@Override
	public ServiceResponse objectToServiceResponse(ServiceRequest request,
			Void response,Method method) {
		return ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.retInfo(request);
	}

	@Override
	public ServiceRequest ObjectToServiceRequest(ServiceRequest request) {
		return request;
	}

	@Override
	public Void serviceResponseToObject(ServiceResponse response,Type type) {
		return null;
	}

}
