package com.iec.core.app.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 * spring容器的上下文
 * @author LUBANG713
 * @date 2014-4-9
 */
public class BeanContext implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	private static String config = "spring-conf/ApplicationContext.xml";

	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public  void  setApplicationContext(ApplicationContext context)
	{
		applicationContext = context;
	}
	
	public static void setContext(ApplicationContext context){
		applicationContext = context;
	}
	
	public static Object getBean(String beanId){
		if(applicationContext == null){
			System.out.println("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getBean(beanId);
		
	}
	
	public static  <T> T getBean(String beanId,Class<T> clazz){
		if(applicationContext == null){
			System.out.println("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getBean(beanId, clazz);
	}
	
	public static Resource getResource(String name){
		if(applicationContext == null){
			System.out.println("getBean from ClassPathXmlApplicationContext");
			applicationContext = new ClassPathXmlApplicationContext(config);
		}
		
		return applicationContext.getResource(name);
	}
}
