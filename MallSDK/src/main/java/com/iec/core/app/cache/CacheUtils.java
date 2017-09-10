package com.iec.core.app.cache;

import java.util.Calendar;
import java.util.Date;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
/**
 * 
 * @ClassName: CacheUtils
 * @Description: 缓存工具类
 * @author Xiehj
 * @date 2011-9-1 下午07:00:33
 *
 */
public class CacheUtils {

	private static final IMemcachedCache CACHE;

	static {
		ICacheManager<IMemcachedCache> manager = CacheUtil.getCacheManager(
				IMemcachedCache.class, MemcachedCacheManager.class.getName());
		manager.setConfigFile("memcached_cluster.xml");
		manager.start();
		CACHE = manager.getCache("mclient1");
	}

	public static IMemcachedCache getCacheInstance(){
		return CACHE;
	}
	
	/**
	 * 按指定分钟后过期
	 * @param hours
	 * @return
	 */
	public static Date setOutTimeMinusAfter(int minute){		
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}
	
	/**
	 * 按指定小时后过期
	 * @param hours
	 * @return
	 */
	public static Date setOutTimeHoursAfter(int hours){		
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.HOUR, hours);
		return c.getTime();
	}
	
	
	/**
	 * 零点过期
	 * @param hours
	 * @return
	 */
	public static Date setOutTimeBy24(){
		Calendar c = Calendar.getInstance();   
		c.set(Calendar.HOUR_OF_DAY, 24);
		return c.getTime();	   
	}
	
	/**
	 * 指定第二天过期时间
	 * @param hours
	 * @return
	 */
	public static Date setOutTimeByHours(int hours){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
		c.set(Calendar.HOUR_OF_DAY, 8);
		return c.getTime();
	}
}
