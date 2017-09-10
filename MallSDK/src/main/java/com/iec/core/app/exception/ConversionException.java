package com.iec.core.app.exception;

/**
 * 
 * @author Tumq
 * @Date 2015年1月9日
 * @describe
 */
public class ConversionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9201987929517550096L;
	
	public ConversionException(){
		
	}
	
	public ConversionException(String msg){
		super(msg);
	}
	
	
	public ConversionException(String msg ,Throwable cause){
		super(msg ,cause);
	}
	
	public ConversionException(Throwable cause){
		super(cause);
	}
}
