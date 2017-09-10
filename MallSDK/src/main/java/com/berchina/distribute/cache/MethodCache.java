package com.berchina.distribute.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 创建于:2016年8月22日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 方法级缓存注解
 * @author chender
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodCache {
	/**
	 * 数据服务路径
	 * @return
	 */
    Class<?> service();
    /**
     * 返回的数据类型(如果返回List<T>,该属性应该填T.class)
     */
    Class<?> resultType();
    
    /**
     * 缓存时间，小于等于0代表永久缓存
     * @return
     */
    int cacheSec();
}