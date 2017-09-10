package com.iec.core.app.exception;

import org.apache.commons.lang.StringUtils;


/**
 * ESB异常类
 * @author ex_kjkfb_lvrz
 * @Date 2014年4月16日 下午4:04:55
 * @description ESB异常
 * @Version V1.0
 */
public class ESBException extends Exception {

	private static final long serialVersionUID = 5696258901927258154L;
	private String resultCode = StringUtils.EMPTY;
	
	private String msg;
	public ESBException(){
		
	}
	
	public ESBException(String msg){
		super(msg);
		this.msg = msg;
	}
	
	public ESBException(String msg,String resultCode){
		super(msg);
		this.msg = msg;
		this.resultCode = resultCode;
	}
	
	public ESBException(Throwable cause , String resultCode){
		super(cause);
		this.resultCode = resultCode ;
	}
	
	public ESBException(String msg ,Throwable cause){
		super(msg ,cause);
	}
	
	public ESBException(Throwable cause){
		super(cause);
	}
	
	public String getResultCode(){
		return resultCode;
	}
	
	public void setResultCode(String resultCode){
		this.resultCode = resultCode ;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
