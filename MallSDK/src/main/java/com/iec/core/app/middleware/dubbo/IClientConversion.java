package com.iec.core.app.middleware.dubbo;

import java.lang.reflect.Type;

import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;

/**
 * @author tump
 * @Date 2015年1月8日
 * @describe 客户端转换器
 */
public interface IClientConversion<R,Q> {
	/**
	 * 将业务对象转换为通用请求对象
	 * @param request
	 * @return
	 */
	ServiceRequest ObjectToServiceRequest(R request) throws ConversionException;
	
	/**
	 * 将通用响应对象转换为业务对象
	 * 
	 * @param response
	 * @return
	 */
	Q serviceResponseToObject(ServiceResponse response,Type type) throws ConversionException;
}
