package com.iec.core.app.middleware.dubbo.conversion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.iec.core.app.middleware.dubbo.service.ServiceBase;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.exception.ConversionException;
import com.iec.core.app.base.global.ResponseInfoEnum;
import com.iec.core.app.middleware.dubbo.IClientConversion;
import com.iec.core.app.middleware.dubbo.IServiceConversion;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;
import com.iec.core.app.middleware.dubbo.ServiceMethodResponse;
import com.iec.core.app.utils.GsonUtils;
import com.iec.core.app.utils.TypeUtils;

/**
 * 自动转换器
 * 
 * @author Tumq
 * @Date 2015年1月27日
 * @describe
 */
public class AutoConversion implements IClientConversion<ServiceRequest,Object>,IServiceConversion<Object,Object>{
	
	private final static String LIST = "list";
	private final static JsonParser parser = new JsonParser();
	private final static Gson gson = new Gson();
	
	public static void insertListToBase(List list,ServiceBase serviceBase){
		insertListToBase(LIST, list, serviceBase);
	}
	
	public static List getListFromBase(ServiceBase serviceBase,Type type){
		return getListFromBase(LIST, serviceBase, type);
	}
	
	public static void insertListToBase(String listKey,List list,ServiceBase serviceBase){
		serviceBase.getBodyMap().put(listKey, jsonStringToList(gson.toJson(list)));
	}
	
	public static List getListFromBase(String listKey,ServiceBase serviceBase,Type type){
		if(List.class.isAssignableFrom((Class)TypeUtils.getTypeAsParameterizedType(type).getRawType())){
			List list = (List)serviceBase.getBodyMap().get(listKey);
			return gson.fromJson(gson.toJson(list), type);
		}
		return null;
	}
	
	public static Map<String, Object> objectToMap(Object object) {
		return jsonStringToMap(gson.toJson(object));
	}

	public static <T> T mapToObject(Map<String, Object> map, Class<T> type) {
		return gson.fromJson(gson.toJson(map), type);
	}

	public static <T> T mapToObject(Map<String, Object> map, Type type) {
		return gson.fromJson(gson.toJson(map), type);
	}
	
	public static List<Object> jsonStringToList(String json) {
		JsonElement jsonObj = parser.parse(json);
		if(jsonObj.isJsonArray()){
			return jsonArraytoList(jsonObj.getAsJsonArray());
		}
		throw new ConversionException("json string cann't convert list");
	}

	/**
	 * 根据json字符串返回Map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonStringToMap(String json) {
		JsonElement jsonObj = parser.parse(json);
		if(jsonObj.isJsonObject()){
			return jsonObjecttoMap(jsonObj.getAsJsonObject());
		}
		throw new ConversionException("json string cann't convert map");
	}

	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonObjecttoMap(JsonObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
		for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter
				.hasNext();) {
			Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			JsonElement value = entry.getValue();
			if (value.isJsonArray())
				map.put((String) key, jsonArraytoList(value.getAsJsonArray()));
			else if (value.isJsonObject())
				map.put((String) key, jsonObjecttoMap(value.getAsJsonObject()));
			else if (value.isJsonPrimitive()) {
				map.put(key,
						getValueOfJsonPrimitive(value.getAsJsonPrimitive()));
			} else if (value.isJsonNull()) {
				map.put(key, "");
			}
		}
		return map;
	}

	/**
	 * 获取JsonPrimitive对象的值
	 * 
	 * @param jsonElement
	 * @return
	 */
	private static Object getValueOfJsonPrimitive(JsonPrimitive jsonElement) {
		try {
			if(jsonElement.isString())
				return jsonElement.getAsString();
			Field filed = JsonPrimitive.class.getDeclaredField("value");
			filed.setAccessible(true);
			Object value = filed.get(jsonElement);
			if(value instanceof BigDecimal){
				return jsonElement.getAsBigDecimal();
			}else if(value instanceof Integer){
				return jsonElement.getAsInt();
			}else if(value instanceof Date){
				return value;
			}else{
				return jsonElement.getAsString();
			}
		} catch (NoSuchFieldException e) {
			return jsonElement.getAsString();
		}catch (SecurityException e) {
			return jsonElement.getAsString();
		}catch (IllegalArgumentException e) {
			return jsonElement.getAsString();
		}catch (IllegalAccessException e) {
			return jsonElement.getAsString();
		}
	}
	
	/**
	 * 将通用的JsonElement转化为java标准对象
	 * 
	 * @param value
	 * @return
	 */
	private static Object jsonElementToObject(JsonElement value){
		return value.isJsonArray() ? jsonArraytoList(value.getAsJsonArray())
				: (value.isJsonObject() ? jsonObjecttoMap(value.getAsJsonObject())
						: (value.isJsonPrimitive() ? getValueOfJsonPrimitive(value
								.getAsJsonPrimitive()) : ""));
	}

	/**
	 * 将JSONArray对象转换成List集合
	 * 
	 * @param json
	 * @return
	 */
	private static List<Object> jsonArraytoList(JsonArray json) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < json.size(); i++) {
			JsonElement value = json.get(i);
			list.add(jsonElementToObject(value));
		}
		return list;
	}
	
	@Override
	public ServiceRequest ObjectToServiceRequest(ServiceRequest request)
			throws ConversionException {
		return request;
	}

	@Override
	public Object serviceResponseToObject(ServiceResponse response,Type type)
			throws ConversionException {
		
		if(TypeUtils.isClass(type) && ServiceResponse.class.isAssignableFrom(TypeUtils.getTypeAsClass(type))){
			return response;
		}else if(TypeUtils.isParameterizedType(type) && 
				List.class.isAssignableFrom((Class)TypeUtils.getTypeAsParameterizedType(type).getRawType())){
			
			return getListFromBase(response, type);
		}
		return mapToObject(response.getBodyMap(), type);
	}

	@Override
	public Object serviceRequestToObject(ServiceRequest request,Method method)
			throws ConversionException {
		Class[] paraTypes = method.getParameterTypes();
		if(ServiceMethodRequest.class.equals(paraTypes[0])){
		    ServiceMethodRequest serviceRequest = new ServiceMethodRequest();
		    serviceRequest.getHeadMap().putAll(request.getHeadMap());
	        serviceRequest.getBodyMap().putAll(request.getBodyMap());
            return serviceRequest;
        }
		else if(ServiceRequest.class.isAssignableFrom(paraTypes[0])){
			return request;
		}else if(Map.class.isAssignableFrom(paraTypes[0])){
			return request.getBodyMap();
		}else if(List.class.isAssignableFrom(paraTypes[0])){
			return request.getBodyValue(LIST);
		}
		return mapToObject(request.getBodyMap(), paraTypes[0]);
	}

	@Override
	public ServiceResponse objectToServiceResponse(ServiceRequest request,
			Object response,Method method) throws ConversionException {
		if(response == null){
			return ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.retInfo(request);
		}
		if(ServiceResponse.class.isAssignableFrom(response.getClass()) ){
			return (ServiceResponse) response;
		}else if(response.getClass().equals(Void.class)){
			return ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.retInfo(request);
		}
		ServiceResponse serviceResponse = ResponseInfoEnum.RECIPROCAL_REQ_SUCESS.retInfo(request);
		if(response instanceof List){
			insertListToBase((List) response, serviceResponse);
		}else if(response instanceof Map){
			serviceResponse.getBodyMap().putAll((Map<? extends String, ? extends Object>) response);
		}else{
			serviceResponse.getBodyMap().putAll(objectToMap(response));
		}
		return serviceResponse;
	}

}
