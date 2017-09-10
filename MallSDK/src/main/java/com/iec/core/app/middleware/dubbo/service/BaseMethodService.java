package com.iec.core.app.middleware.dubbo.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.iec.core.app.middleware.RequestMapping;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.ClassObjectCacha;
import com.iec.core.app.middleware.dubbo.IDubboService;
import com.iec.core.app.middleware.dubbo.IMethodScan;
import com.iec.core.app.middleware.dubbo.IServiceConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;
import com.iec.core.app.middleware.dubbo.ServiceMethodResponse;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;
import com.iec.core.app.utils.DateUtil;
import com.iec.core.app.utils.SystemIp;

/**
 * @author tumq
 * @Date 2015年1月8日
 * @describe 基础服务实现类
 */
@SuppressWarnings("unused")
public class BaseMethodService implements IDubboService {
	private ApplicationContext applicationContext;
	private IMethodScan scanMethod;
	private Map<String, Method> methodMap;
	private static final String SYS_NAME = "ebcp";
	private static final Logger log = LoggerFactory.getLogger(BaseMethodService.class);
	
	public BaseMethodService(IMethodScan scanMethod) {
		this.scanMethod = scanMethod;
	}

	@Override
	public void init(ApplicationContext applicationContext) throws Exception {
		this.applicationContext = applicationContext;
		methodMap = scanMethod.scan(applicationContext,new Class[] { this.getClass() });
		RequestMapping service = null;
		for (Method method : methodMap.values()) {
			service = method.getAnnotation(RequestMapping.class);
			ClassObjectCacha.setObject(service.paraConversionClass());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ServiceResponse invoke(ServiceRequest request) {
        /**
         * updateby sjf
         * 以下内容注释
         */
		//request.setReqSeqNo(BusinessTrackNumber.getBusinessTrackNumber(SYS_NAME));
		log.info("\n请求:\nHead:" + request.getHeadSet() + "\nbody:"+ request.getBodySet());
		String moduleRequestMethodId = request.getHeadValue(ServiceMethodRequest.REQUEST_METHOD_ID).toString();
		Method method = methodMap.get(moduleRequestMethodId);
		ServiceResponse serviceResponse = null;
		if (null != method) {
			RequestMapping service = method.getAnnotation(RequestMapping.class);
			try {
				/* 到conversionMap里取相关类对象 */
				IServiceConversion paraConversion = ClassObjectCacha.getObject(service.paraConversionClass());
				ServiceResponse response = paraConversion.objectToServiceResponse(request, method.invoke(this,paraConversion.serviceRequestToObject(request,method)),method);
				response.setReqSeqNo(request.getReqSeqNo());
				if (response.getClass().equals(ServiceResponse.class)) {
                    serviceResponse = response;
                }else{
                    serviceResponse = new ServiceResponse();
                    serviceResponse.getHeadMap().putAll(response.getHeadMap());
                    serviceResponse.getBodyMap().putAll(response.getBodyMap());
                }


			} catch (ConversionException e) {
				log.error(e.getMessage(), e);
				serviceResponse = ResponseInfoEnum.RECIPROCAL_APP_PARA_CONVERSION_ERROR
						.retInfo(request);
			}catch (Exception e) {
				log.error(e.getMessage(), e);
				serviceResponse = ResponseInfoEnum.RECIPROCAL_APP_SYS_ERROR
						.retInfo(request);
			} 
		} else {
			serviceResponse = (ServiceResponse) ResponseInfoEnum.RECIPROCAL_REQ_METHOD_NOT_EXISTS
					.retInfo(request);
		}
		if(StringUtils.isBlank((serviceResponse.getRespCode()))){
			serviceResponse.setRespCode(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode());
			serviceResponse.setRespMsg(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getDesc());
		}
		//返回头信息设置
		serviceResponse.setHeadValue("svcRtnTime",DateUtil.getFormatCurrentTime("yyyy-MM-dd HH:mm:ss"));
		serviceResponse.setHeadValue("svcIp",SystemIp.getHostIp());
		log.info("\n响应:\nHead:" + serviceResponse.getHeadSet() + "\nbody:"
				+ serviceResponse.getBodySet());
		return serviceResponse;
	}
	
}
