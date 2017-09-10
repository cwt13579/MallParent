package com.berchina.distribute.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @ClassName:     BerServiceImpl.java
 * @Description:   服务实现注解
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:13:15
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BerServiceImpl {
	
	
	/**
	 * 服务标识，可以是版本，可以是实现类型
	 * @return
	 */
	String tag() default "";
	/**
	 * 接口描述
	 * @return
	 */
	String desc() default "";
	
}