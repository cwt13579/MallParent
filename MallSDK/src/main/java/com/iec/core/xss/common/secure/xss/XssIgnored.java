package com.iec.core.xss.common.secure.xss;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注需要忽略过滤的参数
 * @author LUBANG713
 * @date 2014-4-21
 */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XssIgnored
{
	public String[] ignoreParameters();
}
