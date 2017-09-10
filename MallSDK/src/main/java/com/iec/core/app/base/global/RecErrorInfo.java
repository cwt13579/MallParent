package com.iec.core.app.base.global;



/**
 * 
 * 向PHP端返回异常信息(限于在数据接收层中使用)
 * @author LiuSQ
 *
 */
public class RecErrorInfo {

	
	/**
	 * Web请求模块错误(模块不存在)
	 * @return JSON : {"code":1,"desc":"Web request module not exists"}
	 */
	public static String reWebReqModuleError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_REQMODULENOTEXISTS,ErrorCode.ERR_CODE_11);
	}
	
	
	/**
	 * Web请求方法错误(方法不存在)
	 * @return JSON : {"code":1,"desc":"Web request method not exists"}
	 */
	public static String reWebReqMethodError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_REQMETHODNOTEXISTS,ErrorCode.ERR_CODE_12);
	}
	
	
	/**
	 * Web发送参数错误(Java实体bean注入错误)
	 * @return JSON : {"code":1,"desc":"Web pass parameters error to java bean into error"}
	 */
	public static String reWebParamBeanIntoError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_PARBEANINTOERROR,ErrorCode.ERR_CODE_13);
	}
	
	
	
	/**
	 * Web发送参数错误(参数为null或为"")
	 * @return JSON : {"code":1,"desc":"Web pass parameters error"}
	 */
	public static String reWebParamError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_PARERROR,ErrorCode.ERR_CODE_14);
	}
	
	
	/**
	 * app系统错误
	 * @return JSON : {"code":1,"desc":"app system error"}
	 */
	public static String reAppSysError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_PARERROR,ErrorCode.ERR_CODE_15);
	}
	
	public static String reAppSysError(String errCode,String errMsg){
		return RecErrorInfo.reError(errMsg,errCode);
	}
	
	/**
	 * app与数据库交互错误码
	 * @return JSON : {"code":1,"desc":"app and db error"}
	 */
	public static String reAppDBError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_PARERROR,ErrorCode.ERR_CODE_16);
	}
	
	/**
	 * 
	 * @return JSON : {"code":1,"desc":"app and db error"}
	 */
	public static String reAppFieldsDefineError(){
		return RecErrorInfo.reError(RetMsg.RECIPROCAL_PARERROR,ErrorCode.ERR_CODE_16);
	}
	
	/**
	 * 数据库异常
	 * @param errCode
	 * @param errMsg
	 * @return
	 */
	public static String reAppDBError(String errCode,String errMsg){
		return RecErrorInfo.reError(errMsg,errCode);
	}

	/**
	 * 
	 * 返回Web错误信息
	 * @param retMsg
	 * @return JSON : {"code":1,"desc":retMsg}
	 */
	private static String reError(String retMsg,String errcode){
		
		StringBuffer reJson = new StringBuffer("");
		
		reJson.append("{\"code\":\""+errcode+"\",\"data\":\"\",\"desc\":\""+retMsg+"\"}");
		
		return reJson.toString();

	}
	
	
}
