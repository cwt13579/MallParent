package com.iec.core.app.base.global;


/**
 * 
 * 记录返回PHP异常信息
 * @author LiuSQ
 *
 */
public class RetMsg {
	
	/**WEB请求模块不存在**/
	public final static String RECIPROCAL_REQMODULENOTEXISTS = "web request module not exists";
	
	/**WEB请求方法不存在**/
	public final static String RECIPROCAL_REQMETHODNOTEXISTS = "web request method not exists";
	
	/**WEB传递参数错误 Java实体bean注入错误**/
	public final static String RECIPROCAL_PARBEANINTOERROR = "web pass parameters error to java bean into error";

	/**WEB传递参数错误**/
	public final static String RECIPROCAL_PARERROR = "web pass parameters error";
	
	/**app系统错误**/
	public final static String RECIPROCAL_APPSYSERROR = "app system error";
	
	/**app与数据库交互错误码**/
	public final static String RECIPROCAL_APPDBERROR = "app and db error";
	
	/**app接口与定义不符**/
	public final static String RECIPROCAL_APPFIELDSDEFINEERROR = "app fields define differ";

	
}
