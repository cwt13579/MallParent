package com.iec.core.app.core;


/**
 * @Description:请求对象
 * @Author berchina
 * @CreateDate:2013-05-11
 * @version:v1.0
 * 
 */
public class RequestBean {
	
	/**方法名**/
	private String method;
	
	/**方法参数**/
	private String params;
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}

}
