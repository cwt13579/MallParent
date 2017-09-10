package com.iec.core.app.middleware.dubbo.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 服务请求和响应对象基础类，定义服务请求和响应对象的公共属性和方法
 * 
 * @author SCRCU
 * @version 1.0.0
 * @see com.scrcu.ebank.common.service.ServiceRequest
 * @see com.scrcu.ebank.common.service.ServiceResponse
 */
public class ServiceBase implements Serializable {

	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = 2962334299506571300L;
	/**
	 * 报文头
	 */
	protected HashMap<String, Object> head = new HashMap<String, Object>(30,
			0.7f);
	/**
	 * 报文体
	 */
	protected HashMap<String, Object> body = new HashMap<String, Object>(100,
			0.5f);

	/**
	 * 获取指定报文头值
	 * 
	 * @param key
	 *            报文头标识
	 * @return 报文头值
	 */
	public Object getHeadValue(String key) {
		return head.get(key);
	}

	/**
	 * 设置指定报文头值
	 * 
	 * @param key
	 *            报文头标识
	 * @param value
	 *            报文头值
	 */
	public void setHeadValue(String key, Object value) {
		head.put(key, value);
	}

	/**
	 * 获取指定报文体值
	 * 
	 * @param key
	 *            报文体标识
	 * @return 报文体值
	 */
	public Object getBodyValue(String key) {
		return body.get(key);
	}

	/**
	 * 设置指定报文体值
	 * 
	 * @param key
	 *            报文体标识
	 * @param value
	 *            报文体值
	 */
	public void setBodyValue(String key, Object value) {
		body.put(key, value);
	}

	/**
	 * 从报文体中移除指定键的值
	 * 
	 * @param key
	 * @return 与 key 关联的旧值；如果 key 没有任何映射关系，则返回 null
	 */
	public Object removeBodyValue(String key) {
		return body.remove(key);
	}

	/**
	 * 获取 {@link Map} 形式的报文头集合
	 * 
	 * @return 报文头集合
	 */
	public Map<String, Object> getHeadMap() {
		return head;
	}

	/**
	 * 获取 {@link Map} 形式的报文体集合
	 * 
	 * @return 报文体集合
	 */
	public Map<String, Object> getBodyMap() {
		return body;
	}

	/**
	 * 获取报文头集合，可以利用foreach或iterator逐条获取
	 * 
	 * @return 报文头集合
	 */
	public Set<Entry<String, Object>> getHeadSet() {
		return head.entrySet();
	}

	/**
	 * 获取报文体集合，可以利用foreach或iterator逐条获取
	 * 
	 * @return 报文体集合
	 */
	public Set<Entry<String, Object>> getBodySet() {
		return body.entrySet();
	}

	/**
	 * 清空报文头
	 */
	public void clearHead() {
		head.clear();
	}

	/**
	 * 清空报文体
	 */
	public void clearBody() {
		body.clear();
	}

	/**
	 * 报文头是否存在指定键
	 * 
	 * @param key
	 * @return 是否存在
	 */
	public boolean containHeadKey(String key) {
		return head.containsKey(key);
	}

	/**
	 * 报文体是否存在指定键
	 * 
	 * @param key
	 * @return 是否存在
	 */
	public boolean containBodyKey(String key) {
		return body.containsKey(key);
	}
}
