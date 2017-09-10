package com.berchina.distribute.cache;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 创建于:2016年8月22日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * 方法级缓存接口
 * 
 * @author cwt
 * @version 1.0
 * 
 */
public abstract class MethodCacheAspect {
	private static final Logger logger = LoggerFactory.getLogger("METHOD_CACHE");
	private static final String METHODCACHEKEY="_MRTHODCACHEKEY_";
	
	 
	@Around("@annotation(com.berchina.distribute.cache.MethodCache)")
	public  Object cache(ProceedingJoinPoint jp) throws Throwable {
		String methodName = jp.getSignature().getName();
		Method method = ((MethodSignature) jp.getSignature()).getMethod();
		MethodCache mc=method.getAnnotation(MethodCache.class);
		if(mc==null){//没有配置缓存策略
			return jp.proceed(jp.getArgs());
		}

		String clzName = mc.service().getName();
		String redisKey = buildCacheKey(clzName, methodName, jp.getArgs());
		
		logger.info("cache hit，key [{}]", redisKey);
		
		String json=obtainFromCache(redisKey);
		if(json!=null&&!"".equals(json)){//返回缓存值
			return parseToObject(json, ((MethodSignature) jp.getSignature()).getReturnType(), mc.resultType());
		}
		Object result=jp.proceed(jp.getArgs());
		storeToCache(redisKey, JSON.toJSONString(result),mc.cacheSec());
		return result;	
	}
	
	/**
	 * 从缓存中获取值
	 * @param key 缓存键
	 * @return
	 */
	protected abstract String obtainFromCache(String key);
	
	/**
	 * 将数据保存到缓存中
	 * @param key 缓存键
	 * @param val 缓存数据
	 * @param sec 缓存时间，小于等于0代表永久缓存
	 */
	protected abstract void storeToCache(String key,String val,int sec);
	
	private String buildCacheKey(String clzName,String methodName,Object[] args){
		String result=METHODCACHEKEY+clzName+"@"+methodName;
		if(args!=null&&args.length!=0){
			result+="@"+JSONArray.toJSONString(args);
		}
		return result;
	}
	
	private Object parseToObject(String jsonString, Class<?> resultType, Class<?> modelType) {
		if (resultType.isAssignableFrom(List.class)) {
			return JSON.parseArray(jsonString, modelType);
		}
		return JSON.parseObject(jsonString, resultType);
	}
}
