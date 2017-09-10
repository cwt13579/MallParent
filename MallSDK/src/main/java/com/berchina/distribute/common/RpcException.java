package com.berchina.distribute.common;

/**
 * 
 * @ClassName:     RpcException.java
 * @Description:   rpc异常
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:10:36
 */
public class RpcException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsg;
	private Throwable bizException;
	
	public RpcException(){
		super();
	}
	
	public RpcException(String errorCode,String errorMsg){
		super();
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}
	
	
	public RpcException(String errorCode,String errorMsg,Throwable bizException){
		super();
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
		this.bizException=bizException;
	}
	
	public RpcException(String errorCode,Throwable bizException){
		super();
		this.errorCode=errorCode;
		this.bizException=bizException;
	}
	
	public String getMessage(){
		return "错误码:"+errorCode+"错误描述:"+errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Throwable getBizException() {
		return bizException;
	}
	
}
