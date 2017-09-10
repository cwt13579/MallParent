package com.iec.core.app.base.global;

import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;



/**
 * @author lvrz
 * @Date 12 Dec 2014
 * @describe
 */
public enum ResponseInfoEnum {
	
	RECIPROCAL_REQ_SUCESS("0000000000","web request sucess"),
	/**
	 * Web请求方法错误(方法不存在)
	 * */
	RECIPROCAL_REQ_METHOD_NOT_EXISTS("11","web request method not exists"),
		/**Web发送参数错误(Java实体bean注入错误)*/
	RECIPROCAL_PARBEAN_INTO_ERROR("13","web pass parameters error to java bean into error"),
		/**Web发送参数错误*/
	RECIPROCAL_PARAM_ERROR("14","web pass parameters error"),
		/** app系统错误*/
	RECIPROCAL_APP_SYS_ERROR("EBCP999999","app system error"),
		/**app与数据库交互错误码*/
	RECIPROCAL_APP_DB_ERROR("16","app and db error"),
		/**app接口与定义不符*/
	RECIPROCAL_APP_FIELDSDEFINEE_RROR("17","app fields define differ"),
		/**app dao错误*/
	RECIPROCAL_DAO_ERROR("18","app dao error"),
		/**app Service错误*/
	RECIPROCAL_SERVICE_ERROR("19","app service error"),
		/**app controller错误*/
	RECIPROCAL_CONTROLLER_ERROR("20","app controller error"),
	/**ESB Service错误*/
	RECIPROCAL_ESB_SERVICE_ERROR("21","esb service error"),
	/**
	 * json转换异常
	 * */
	RECIPROCAL_JSON_PARSE_ERROR("22","json parse error"),
	/**
	 * app端参数转换出错
	 */
	RECIPROCAL_APP_PARA_CONVERSION_ERROR("23","app parse consersion error"),
	
	RECIPROCAL_APP_DB_NOTENTITY_ERROR("24","app db return not  entity"),
	
	RECIPROCAL_PARAMS_NULL_ERROR("25","params is null error"),
	
	RECIPROCAL_BUSINESS_PROMPT("111111","具体业务信息提示");
	      String code;
          String desc;

          
    public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	private ResponseInfoEnum(String code,String desc){
    	  this.desc=desc;
    	  this.code=code;
    }

    public  String retInfo (){
  		return retInfo(code,desc);
  	}
    public static String retInfo (String code,String desc){
  		StringBuffer reJson = new StringBuffer("");
  		
  		reJson.append("{\"code\":\""+code+"\",\"data\":\"\",\"desc\":\""+desc+"\"}");
  		
  		return reJson.toString();

  	}
    public ServiceResponse retInfo(ServiceRequest request){
    	ServiceResponse response = new ServiceResponse();
    	response.setRespCode(code);
    	response.setRespMsg(desc);
    	response.setReqSeqNo(request.getReqSeqNo());
    	return response;
    }
}
