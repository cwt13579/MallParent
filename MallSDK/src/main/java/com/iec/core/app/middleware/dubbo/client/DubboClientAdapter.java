package com.iec.core.app.middleware.dubbo.client;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcContext;
import com.google.gson.reflect.TypeToken;
import com.iec.core.app.middleware.IClient;
import com.iec.core.app.middleware.dubbo.client.IServiceInvoker;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ServiceException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.ClassObjectCacha;
import com.iec.core.app.middleware.dubbo.IClientConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;
import com.iec.core.app.middleware.dubbo.ServiceMethodResponse;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;

/**
 * @author tump
 * @Date 2015年1月8日
 * @describe 客户端发送请求适配器
 */
public class DubboClientAdapter extends AbstractClient implements IClient,
		IServiceInvoker {
	private static Logger logger= LoggerFactory.getLogger(DubboClientAdapter.class);
	private static final TypeToken<AutoConversion> TYPETOKEN = new TypeToken<AutoConversion>(){};

	private IServiceInvoker iServiceInvoker;

	public IServiceInvoker getiServiceInvoker() {
		return iServiceInvoker;
	}

	public void setiServiceInvoker(IServiceInvoker iServiceInvoker) {
		this.iServiceInvoker = iServiceInvoker;
	}

	@Override
	public Future<ServiceResponse> asyncCall(ServiceRequest arg0) throws Exception {
	    setRequestCommon(arg0);
		return iServiceInvoker.asyncCall(arg0);
	}

	@Override
	public ServiceResponse call(ServiceRequest arg0) throws Exception {
	    setRequestCommon(arg0);
		return iServiceInvoker.call(arg0);
	}

	@Override
	public ServiceMethodResponse send(ServiceRequest request)
			throws ServiceException {
	    ServiceResponse serviceResponse = null;
	    try{
	        if(request instanceof ServiceMethodRequest){
	            serviceResponse = call(((ServiceMethodRequest)request).copyToServiceRequest());
	        }else{
	            serviceResponse = call(request);
	        }
	        
	    }catch(Exception e){
	    	logger.error("*****APP系统异常*****", e);
	        throw new ServiceException(e);
	    }
	    if(!serviceResponse.getRespCode().equals(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode())){
            throw new ServiceException(serviceResponse.getRespMsg(), serviceResponse.getRespCode());
        }
	    ServiceMethodResponse response = new ServiceMethodResponse();
	    response.getHeadMap().putAll(serviceResponse.getHeadMap());
	    response.getBodyMap().putAll(serviceResponse.getBodyMap());
		return response;
	}
	
	@Override
	public Future<ServiceMethodResponse> asyncSend(final ServiceRequest request) {
		return  RpcContext.getContext().asyncCall(new Callable<ServiceMethodResponse>() {

			@Override
			public ServiceMethodResponse call() throws Exception {
				return DubboClientAdapter.this.send(request);
			}
		});
	}
	
	@Override
	public <Q> Q sendByConversion(ServiceRequest request,
			IClientConversion<ServiceRequest, Q> clientConversionClass)
			throws ServiceException {
		ServiceResponse serviceResponse = null;
		try{
			serviceResponse = send(request);
		}catch(Exception e){
			logger.error("*****APP系统异常*****", e);
			throw new ServiceException(e);
		}
		if(!serviceResponse.getRespCode().equals(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode())){
			throw new ServiceException(serviceResponse.getRespMsg(), serviceResponse.getRespCode());
		}
		final TypeToken<Q> typeToken = new TypeToken<Q>(){};
		return clientConversionClass.serviceResponseToObject(serviceResponse,typeToken.getType());
	}
	
	@Override
	public <Q> Future<Q> asyncSendByConversion(final ServiceRequest request,
			final IClientConversion<ServiceRequest, Q> paraConversion)
			 {
		final TypeToken<Q> typeToken = new TypeToken<Q>(){};
		return RpcContext.getContext().asyncCall(new Callable<Q>() {
			@Override
			public Q call() throws Exception {
				return paraConversion
						.serviceResponseToObject(DubboClientAdapter.this
								.send(request),typeToken.getType());
			}
		});
	}
	
	@Override
	public <Q> Q send(ServiceRequest request, TypeToken<Q> responseClass)
			throws ServiceException {
		AutoConversion paraConversion = ClassObjectCacha.getObject(TYPETOKEN);
		ServiceResponse serviceResponse = null;
		try{
			serviceResponse = send(request);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		if(!serviceResponse.getRespCode().equals(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode())){
			throw new ServiceException(serviceResponse.getRespMsg(), serviceResponse.getRespCode());
		}
		return (Q) paraConversion.serviceResponseToObject(serviceResponse,responseClass.getType());
	}

	@Override
	public <Q> Q send(ServiceRequest request, Class<Q> responseClass)
			throws ServiceException {
		AutoConversion paraConversion = ClassObjectCacha.getObject(TYPETOKEN);
		ServiceResponse serviceResponse = null;
		try{
			serviceResponse = send(request);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		if(!serviceResponse.getRespCode().equals(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode())){
			throw new ServiceException(serviceResponse.getRespMsg(), serviceResponse.getRespCode());
		}
		return (Q) paraConversion.serviceResponseToObject(serviceResponse,responseClass);
	}


}
