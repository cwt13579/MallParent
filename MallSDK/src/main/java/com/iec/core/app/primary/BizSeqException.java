package com.iec.core.app.primary;

import org.apache.commons.lang.StringUtils;

public class BizSeqException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5696258901927258154L;
	private String resultCode = StringUtils.EMPTY;
	
	public BizSeqException(){
		
	}
	
	public BizSeqException(String msg){
		super(msg);
	}
	
	public BizSeqException(String msg,String resultCode){
		super(msg);
		this.resultCode = resultCode;
	}
	
	public BizSeqException(Throwable cause , String resultCode){
		super(cause);
		this.resultCode = resultCode ;
	}
	
	public BizSeqException(String msg ,Throwable cause){
		super(msg ,cause);
	}
	
	public BizSeqException(Throwable cause){
		super(cause);
	}
	
	public String getResultCode(){
		return resultCode;
	}
	
	public void setResultCode(String resultCode){
		this.resultCode = resultCode ;
	}
}
