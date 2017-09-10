package com.berchina.distribute.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @ClassName:     BerBean.java
 * @Description:   实体
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:12:07
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BerBean {
	/**
	 * 接口描述
	 * @return
	 */
	String desc() default "";
}