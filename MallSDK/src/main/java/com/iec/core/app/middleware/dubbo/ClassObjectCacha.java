package com.iec.core.app.middleware.dubbo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.reflect.TypeToken;

/**
 * @author apple
 *  类对象缓存
 */
public class ClassObjectCacha {
	
	
	private final static Map<String, Object> classObjectMap = new ConcurrentHashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(TypeToken<T> typeToken){
		return (T) getObject(typeToken.getRawType());
	}
	
	public static <T> T getObject(Class<T> clazz){
			try {
				setObject(clazz);
			} catch (InstantiationException e ) {
				return null;
			}catch(IllegalAccessException e){
				return null;
			}
		return (T)classObjectMap.get(clazz.getName());
	}
	
	public static synchronized <T> void setObject(Class<T> clazz) throws InstantiationException, IllegalAccessException{
		if(!classObjectMap.containsKey(clazz.getName())){
			classObjectMap.put(clazz.getName(), clazz.newInstance());
		}
	}
}
