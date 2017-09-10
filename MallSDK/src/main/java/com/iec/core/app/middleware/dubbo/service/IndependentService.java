package com.iec.core.app.middleware.dubbo.service;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.ClassObjectCacha;
import com.iec.core.app.middleware.dubbo.IDubboService;
import com.iec.core.app.middleware.dubbo.IServiceConversion;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;
import com.iec.core.app.utils.DateUtil;
import com.iec.core.app.utils.PropsConfig;
import com.iec.core.app.utils.SystemIp;

/**
 * 独立的服务
 * 
 * @author Tumq
 * @Date 2015年2月3日
 * @describe
 * @param <R> 请求对象类型
 * @param <Q> 返回对象类型
 */
public abstract class IndependentService<R, Q> implements IDubboService {

    private static final Logger log = LoggerFactory.getLogger(IndependentService.class);
    private static final String METHOD_NAME = "invokeRequest";
    private static String SYS_NAME;

    public abstract Q invokeRequest(R request) throws Exception;

    @Override
    public void init(ApplicationContext applicationContext) throws Exception {
        SYS_NAME = PropsConfig.getPropValue("sysName");
    }

    @Override
    public ServiceResponse invoke(ServiceRequest request) {
        Method[] methods = this.getClass().getDeclaredMethods();
        Method invokeRequestMethod = null;
        for (int i=0;i<methods.length;i++) {
            Method method = methods[i];
            if (method.getName().equals(METHOD_NAME)
                    && (!method.getReturnType().equals(Object.class) || !method.getParameterTypes()[0].equals(Object.class))) {
                invokeRequestMethod = method;
                break;
            }
        }

        if (invokeRequestMethod == null) {
            for (int i=0;i<methods.length;i++) {
                Method method = methods[i];   
                if (method.getName().equals(METHOD_NAME)) {
                    invokeRequestMethod = method;
                    break;
                }
            }
        }
        /**
         * updateby sjf
         * 以下内容注释
         */
        /*//避免覆盖其他系统产生的流水号
        if(StringUtils.isBlank(request.getReqSeqNo())){
        	request.setReqSeqNo(BusinessTrackNumber.getBusinessTrackNumber(SYS_NAME));
        }*/
        log.info("\n请求:\nHead:" + request.getHeadSet() + "\nbody:" + request.getBodySet());
        ServiceResponse serviceResponse = null;
        if (invokeRequestMethod != null) {
            try {
                /* 到conversionMap里取相关类对象 */
                IServiceConversion paraConversion = ClassObjectCacha.getObject(AutoConversion.class);
                ServiceResponse response = paraConversion.objectToServiceResponse(request,
                        invokeRequest((R) paraConversion.serviceRequestToObject(request, invokeRequestMethod)),
                        invokeRequestMethod);

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
                serviceResponse = ResponseInfoEnum.RECIPROCAL_APP_PARA_CONVERSION_ERROR.retInfo(request);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                serviceResponse = ResponseInfoEnum.RECIPROCAL_APP_SYS_ERROR.retInfo(request);
                
            }
        }
        if (StringUtils.isBlank((serviceResponse.getRespCode()))) {
            serviceResponse.setRespCode(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getCode());
            serviceResponse.setRespMsg(ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.getDesc());
        }
        // 返回头信息设置
//        serviceResponse.setHeadValue("svcRtnTime", DateUtil.getFormatCurrentTime("yyyy-MM-dd HH:mm:ss"));
        //serviceResponse.setReturnTime(new Date());
        //serviceResponse.setServerIp(SystemIp.getHostIp());
        serviceResponse.setReqSeqNo(request.getReqSeqNo());
        log.info("\n响应:\nHead:" + serviceResponse.getHeadSet() + "\nbody:" + serviceResponse.getBodySet());
        return serviceResponse;
    }
    /**
	 * @param code
	 * @param desc
	 * @param serviceResponse
	 * @return 返回个性化错误信息和错误描述
	 */
	public ServiceResponse rtnErrorInfo(String code,String desc,ServiceResponse serviceResponse){
		serviceResponse.setRespCode(code);
		serviceResponse.setRespMsg(desc);
		return serviceResponse;
		
	}
}
