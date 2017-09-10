
package com.iec.core.app.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class BeanLocator implements ApplicationContextAware {

	private  static ApplicationContext applicationContext = null;
	private  ApplicationContext applicationContext2 = null;
    
    
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext2 = applicationContext;		
		BeanLocator.applicationContext = applicationContext2;
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
