package com.iec.core.app.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 访问注解
 * @author ASUS
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonMapping {
	
	public String mappingName();
	
	public String mappingMemo();

}
