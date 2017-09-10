package com.iec.core.app.middleware;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iec.core.app.middleware.dubbo.IServiceConversion;
import com.iec.core.app.middleware.dubbo.conversion.AutoConversion;


/**
 * 访问注解
 * @author ASUS
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	
	public String id();
	
	public String memo();
	
	@SuppressWarnings("rawtypes")
	public Class<? extends IServiceConversion> paraConversionClass() default AutoConversion.class;
}
