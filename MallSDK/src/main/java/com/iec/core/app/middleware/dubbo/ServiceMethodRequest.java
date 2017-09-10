package com.iec.core.app.middleware.dubbo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;
import com.iec.core.app.utils.TypeUtils;


/**
 * @Description:服务请求对象
 * @Author Tumq
 * 
 */
public class ServiceMethodRequest extends ServiceRequest{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7598672018503771502L;
	public final static String REQUEST_METHOD_ID = "moduleRequestMethodId";


	public String getMethodId() {
		return this.getHeadValue(REQUEST_METHOD_ID).toString();
	}

	public void setMethodId(String methodId) {
		super.setHeadValue(REQUEST_METHOD_ID, methodId);
	}
	
	public void setBodyList(String key,List list){
		AutoConversion.insertListToBase(key, list, this);
	}
	
	public void setBody(Object body){
		if (body instanceof Map) {
			getBodyMap().putAll((Map<? extends String, ? extends Object>) body);
		} else if(body instanceof List){
			AutoConversion.insertListToBase((List)body, this);
		}else {
			getBodyMap().putAll(AutoConversion.objectToMap(body));
		}
	}
	
	public <T> List<T> getBodyList(String key,Type type) {
		if(TypeUtils.isParameterizedType(type) && List.class.isAssignableFrom((Class)TypeUtils.getTypeAsParameterizedType(type).getRawType())){
			return AutoConversion.getListFromBase(key,this, type);
		}
		throw new ConversionException(type + " isn't list");
	}
	
	public <T> T getBody(Type type){
		if(TypeUtils.isParameterizedType(type) && List.class.isAssignableFrom((Class)TypeUtils.getTypeAsParameterizedType(type).getRawType())){
			return (T) AutoConversion.getListFromBase(this, type);
		}
		return AutoConversion.mapToObject(getBodyMap(), type);
	}
	
	public <T> T getBody(Class<T> type){
		if(List.class.isAssignableFrom(type)){
			return (T) AutoConversion.getListFromBase(this, type);
		}
		return AutoConversion.mapToObject(getBodyMap(), type);
	}
	
	public ServiceRequest copyToServiceRequest(){
	    ServiceRequest serviceRequest = new ServiceRequest();
	    serviceRequest.getHeadMap().putAll(this.getHeadMap());
	    serviceRequest.getBodyMap().putAll(getBodyMap());
	    return serviceRequest;
	}
	
	public String toString(){
		return "\nHead:"+getHeadSet()+"\n"+"body:"+getBodySet();
	}

}
