package com.iec.core.app.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.iec.core.app.base.BeanContext;
import com.iec.core.app.base.global.RecErrorInfo;

/**
 * 反射方法类
 * @author ASUS
 * 
 */
public class BaseMapping {
	
	private static final Logger log = Logger.getLogger("IECCORE");
	private static BaseMapping INSTANCE = null;
	private static Map<String, Method> jsonServiceMap = new HashMap<String, Method>();
	
	/*
	static {
		//这里的class改成可配置,class可卸载或追加
		Class[] clazzArray = new Class[] {
				CategoryController.class,
				CaseShareController.class,CacheControler.class,
				DemoController.class
				};
		for (int i = 0; i < clazzArray.length; i++) {
			initJsonServiceMap(clazzArray[i]);
		}
	}
	*/
	
	public BaseMapping() {
		
	}
	
	public static final boolean Init(ArrayClasses ac)
	{
		if (INSTANCE == null) {
			INSTANCE = new BaseMapping();
		}
		
		boolean rt = true;
		
		try {
			int len = ac.getClassNames().length;
			Class[] clazzArray = new Class[len];
			int i = 0;
			for(i=0;i < len;i++) {
				clazzArray[i] = Class.forName(ac.getClassNames()[i]);
			}
			
			for (i = 0; i < len; i++) {
				if(!initJsonServiceMap(clazzArray[i])) {
					rt = false;
					break;
				}
			}
		}
		catch (Exception e) {
			log.error("[BaseMapping error]: class init failed,",e);
			rt = false;
		} 
		return rt;
	}
	
	public static final BaseMapping getInstance()
	{
		if (INSTANCE == null) {
			INSTANCE = new BaseMapping();
		}
		return INSTANCE;
	}

	/**
	 * initial JsonServiceMap, prepare methods with JsonService annotation
	 */
	private static boolean initJsonServiceMap(Class clazz) {
		Method[] method = clazz.getDeclaredMethods();
		for (int i = 0; i < method.length; i++) {
			JsonMapping service = method[i].getAnnotation(JsonMapping.class);
			
			if (null == service)
				continue;
			String serviceName = service.mappingName();
			
			if (jsonServiceMap.containsKey(serviceName)) {
				log.error("[BaseMapping error]: serviceName conflict,"+serviceName);
				return false;
			}
			
			log.info(serviceName +">>>>>"+ clazz.getName());
			
			jsonServiceMap.put(serviceName, method[i]);
		}
		
		return true;
	}

	/**
	 * service entry point
	 * 
	 * @param methodName
	 * @param json
	 * @return
	 */
	public String baseCall(String methodName, String json) {
		
		Method method = jsonServiceMap.get(methodName);
		if (null != method) {
			try {
				return serviceExecutor(method, json);
			} catch (Exception e) {
				log.error("[BaseService error]:", e);
			}
		}
		return RecErrorInfo.reWebReqMethodError();
	}

	/**
	 * service executor for all business
	 * 
	 * @param method
	 * @param json
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private String serviceExecutor(Method method, String json)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		
		String className = method.getDeclaringClass().getSimpleName();
		
		Object obj = BeanContext.getBean(toLowerCaseFirstOne(className));
		
		return (String) method.invoke(obj, new Object[] { json });
	}
	
	public static String toLowerCaseFirstOne(String s){
		if(s == null || s.isEmpty()) return s;
		
		if(Character.isLowerCase(s.charAt(0))){
			return s;
		}else{
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}
	
}
