package com.berchina.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName:     BaseVo.java
 * @Description:   
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 下午4:12:54
 */
public class BaseVo implements Serializable {
	
	private static final long serialVersionUID = -4519368664881557431L;

	/**
	 * 缓存用key
	 */
	public static String UseCacheKey ="useCache"; 
	
	/**
	 * 是否使用缓存
	 */
	private boolean useCache = false;
	
	/**
	 * 排序字段
	 */
	private String orderColumn;
	
	/**
	 * 缓存key
	 */
	private String cacheKey = null;

	public boolean isUseCache() {
		return useCache;
	}
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	
}
