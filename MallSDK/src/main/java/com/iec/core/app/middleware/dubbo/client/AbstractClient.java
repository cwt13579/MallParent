package com.iec.core.app.middleware.dubbo.client;

import java.util.Date;
import java.util.concurrent.Future;

import com.google.gson.reflect.TypeToken;
import com.iec.core.app.middleware.IClient;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.exception.ServiceException;
import com.iec.core.app.middleware.dubbo.ClassObjectCacha;
import com.iec.core.app.middleware.dubbo.IClientConversion;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;
import com.iec.core.app.utils.SystemIp;

public abstract class AbstractClient implements IClient{
    
    public void setRequestCommon(ServiceRequest serviceRequest){
        serviceRequest.setHeadValue("appId","");//应用ID
        serviceRequest.setChannelId("");//渠道ID
        serviceRequest.setRequestTime(new Date());
        serviceRequest.setHeadValue("hostIp", SystemIp.getSysIp());
    }
    
	@Override
	public <Q> Q send(ServiceRequest request, TypeToken<Q> responseClass)
			throws ServiceException {
		AutoConversion paraConversion = ClassObjectCacha.getObject(new TypeToken<AutoConversion>(){});
		return (Q) sendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Q send(ServiceRequest request, Class<Q> responseClass)
			throws ServiceException {
		AutoConversion paraConversion = ClassObjectCacha.getObject(new TypeToken<AutoConversion>(){});
		return (Q) sendByConversion(request, paraConversion);
	}


	@Override
	public <Q> Q sendByConversion(
			ServiceRequest request,
			Class<? extends IClientConversion<ServiceRequest, Q>> clientConversionClass)
			throws ServiceException {
		IClientConversion<ServiceRequest, Q> paraConversion = ClassObjectCacha.getObject(clientConversionClass);
		return sendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Future<Q> asyncSend(ServiceRequest request,
			TypeToken<Q> responseClass)  {
		AutoConversion paraConversion = ClassObjectCacha.getObject(new TypeToken<AutoConversion>(){});
		return (Future<Q>) asyncSendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Future<Q> asyncSend(ServiceRequest request,
			Class<Q> responseClass) {
		AutoConversion paraConversion = ClassObjectCacha.getObject(new TypeToken<AutoConversion>(){});
		return (Future<Q>) asyncSendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Q sendByConversion(
			ServiceRequest request,
			TypeToken<? extends IClientConversion<ServiceRequest, Q>> clientConversionClass)
			throws ServiceException {
		IClientConversion<ServiceRequest, Q> paraConversion = ClassObjectCacha.getObject(clientConversionClass);
		return sendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Future<Q> asyncSendByConversion(
			ServiceRequest request,
			Class<? extends IClientConversion<ServiceRequest, Q>> clientConversionClass)
			 {
		IClientConversion<ServiceRequest, Q> paraConversion = ClassObjectCacha.getObject(clientConversionClass);
		return asyncSendByConversion(request, paraConversion);
	}

	@Override
	public <Q> Future<Q> asyncSendByConversion(
			ServiceRequest request,
			TypeToken<? extends IClientConversion<ServiceRequest, Q>> clientConversionClass)
			 {
		IClientConversion<ServiceRequest, Q> paraConversion = ClassObjectCacha.getObject(clientConversionClass);
		return asyncSendByConversion(request, paraConversion);
	}
}
