package com.berchina.common;

/**
 * 
 * @ClassName:     BerException.java
 * @Description:   业务异常类
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:27:28
 */
public class BerException extends Exception{
	
	private String errCode;
	private String errMsg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5785392751470303800L;
	
	public BerException(){
		super();
	}
	
	public BerException(String errCode,String errMsg){
		super();
		this.errCode=errCode;
		this.errMsg=errMsg;
	}
	
	public BerException(String errCode,String errMsg,Exception e){
		super(e);
		this.errCode=errCode;
		this.errMsg=errMsg;
	}
	public String getErrCode() {
		return errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	@Override
	public String getMessage() {
		return "错误码:"+errCode+",错误描述:"+errMsg+" "+(super.getMessage()==null?"":super.getMessage());
	}
}
