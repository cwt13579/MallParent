package com.iec.core.app.schedule.lock;

import org.apache.commons.lang.StringUtils;

public class ScheduleLockException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5696258901927258154L;
	private String resultCode = StringUtils.EMPTY;
	
	public ScheduleLockException(){
		
	}
	
	public ScheduleLockException(String msg){
		super(msg);
	}
	
	public ScheduleLockException(String msg,String resultCode){
		super(msg);
		this.resultCode = resultCode;
	}
	
	public ScheduleLockException(Throwable cause , String resultCode){
		super(cause);
		this.resultCode = resultCode ;
	}
	
	public ScheduleLockException(String msg ,Throwable cause){
		super(msg ,cause);
	}
	
	public ScheduleLockException(Throwable cause){
		super(cause);
	}
	
	public String getResultCode(){
		return resultCode;
	}
	
	public void setResultCode(String resultCode){
		this.resultCode = resultCode ;
	}
}
