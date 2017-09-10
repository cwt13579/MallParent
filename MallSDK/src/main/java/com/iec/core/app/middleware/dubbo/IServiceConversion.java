package com.iec.core.app.middleware.dubbo;

import java.lang.reflect.Method;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ConversionException;

/**
 * @author tump
 * @Date 2015年1月8日
 * @describe 服务端转换器
 */
public interface IServiceConversion<R,Q> {
	/**
	 * 將通用请求对象转换为业务对象
	 * @param request
	 * @return
	 */
	R serviceRequestToObject(ServiceRequest request,Method method) throws ConversionException;
	
	/**
	 * 将业务对象转换为通用响应对象
	 * @param response
	 * @return
	 */
	ServiceResponse objectToServiceResponse(ServiceRequest request,Q response,Method method) throws ConversionException;
}
