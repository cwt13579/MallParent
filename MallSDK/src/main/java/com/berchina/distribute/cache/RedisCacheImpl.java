package com.berchina.distribute.cache;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.iec.core.app.utils.JedisSentinelUtil;


/**
 * 
 * @ClassName:     RedisCacheImpl.java
 * @Description:   redis方法级缓存实现，该类本来应该写在sdk里面的，但是redis的工具类在share里面，没办法
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月11日 下午2:55:22
 */
@Aspect
@Component
public class RedisCacheImpl extends MethodCacheAspect {

	public RedisCacheImpl() {
		System.out.println("=====================");
	}
	@Override
	protected String obtainFromCache(String key) {
		return JedisSentinelUtil.get(key);
	}


	@Override
	protected void storeToCache(String key, String val,int cacheSec) {
		if(cacheSec>0){
			JedisSentinelUtil.setStringSec(key, val, cacheSec);
		}else{
			JedisSentinelUtil.set(key, val);
		}
	}
}
