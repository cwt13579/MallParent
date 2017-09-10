package com.iec.core.web;

/**
 * @Description:返回码
 * @Author berchina
 * @CreateDate:2012-01-06
 * @version:v1.0
 * 
 */
public class ResultCode {
	
	/**返回成功**/
	public static final String SUCCESS = "0" ;
	
	/**返回失败**/
	public static final String ERROR = "1"  ;
	
	/**连接超时**/
	public static final String TIMEOUT = "2" ;
	
	/**连接错误**/
	public static final String CONNECT_ERROR = "3" ;
	
	/**格式错误**/
	public static final String FORMAT_ERROR ="4";
	
	/**方法名不存在**/
	public static final String METHOD_NOT_EXIST = "5" ;
}
