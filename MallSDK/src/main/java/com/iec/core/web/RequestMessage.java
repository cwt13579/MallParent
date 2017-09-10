package com.iec.core.web;

import java.io.Serializable;

/**
 * @Description:mina传输对象
 * @Author berchina
 * @CreateDate:2012-01-05
 * @version:v1.0
 * 
 */
public class RequestMessage implements Serializable {
	
	private static final long serialVersionUID = -6111220358041616436L;

	/**请求方法名**/
    private String method;
    
    /**请求参数JSON**/
    private String params;
    
    /**请求服务后缀名**/
    private String server;
    
    public RequestMessage(){}

    public RequestMessage(String method,String params){
    	this.method = method;
    	this.params = params;
    }
    /**
     * 
     * @param method  接口名称
     * @param params  接口参数json字符串
     * @param server  访问的APP名
     */
    public RequestMessage(String method,String params,String server){
    	this.method = method;
    	this.params = params;
    	this.server = server;
    }
    
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
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}
