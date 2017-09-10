package com.iec.core.app.middleware;
import java.util.concurrent.Future;
import com.google.gson.reflect.TypeToken;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.exception.ServiceException;
import com.iec.core.app.middleware.dubbo.IClientConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodResponse;

/**
 * 
 * @author Tumq
 *
 */
public interface IClient {
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param responseClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Q send(ServiceRequest request,TypeToken<Q> responseClass) throws ServiceException;
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param responseClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Q send(ServiceRequest request,Class<Q> responseClass) throws ServiceException;
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public ServiceMethodResponse send(ServiceRequest request) throws ServiceException;
	
	/**
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Q sendByConversion(ServiceRequest request,IClientConversion<ServiceRequest,Q> clientConversionClass) throws ServiceException;
	
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Q sendByConversion(ServiceRequest request,Class<? extends IClientConversion<ServiceRequest,Q>> clientConversionClass) throws ServiceException;
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Q sendByConversion(ServiceRequest request,TypeToken<? extends IClientConversion<ServiceRequest,Q>> clientConversionClass) throws ServiceException;
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param responseClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Future<Q> asyncSend(ServiceRequest request,TypeToken<Q> responseClass);
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param responseClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Future<Q> asyncSend(ServiceRequest request,Class<Q> responseClass);
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	public Future<ServiceMethodResponse> asyncSend(ServiceRequest request);
	
	/**
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Future<Q> asyncSendByConversion(ServiceRequest request,IClientConversion<ServiceRequest,Q> clientConversionClass);
	
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Future<Q> asyncSendByConversion(ServiceRequest request,Class<? extends IClientConversion<ServiceRequest,Q>> clientConversionClass) ;
	
	/**
	 * 发送请求信息
	 * 
	 * @param request
	 * @param clientConversionClass
	 * @return
	 * @throws ServiceException
	 */
	public <Q> Future<Q> asyncSendByConversion(ServiceRequest request,TypeToken<? extends IClientConversion<ServiceRequest,Q>> clientConversionClass);
	
}
