package com.berchina.distribute.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @ClassName:     BerService.java
 * @Description:   服务接口注解
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:12:55
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BerService {
	/**
	 * 接口描述
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 系统标识
	 * @return
	 */
	String systemTag() default "";
}