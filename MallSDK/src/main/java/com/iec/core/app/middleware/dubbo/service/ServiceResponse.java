package com.iec.core.app.middleware.dubbo.service;

/**
 * 服务响应对象
 * 
 * @author SCRCU
 * @version 1.0.0
 * @see com.scrcu.ebank.common.service.ServiceBase
 */
public class ServiceResponse extends ServiceBase {

	private static final long serialVersionUID = -8180843661563159781L;

	/**
	 * 获取服务返回码
	 * 
	 * @return 服务返回码
	 */
	public String getRespCode() {
		return (String) super.getHeadValue(IConstant.RESPONSE_CODE);
	}

	/**
	 * 设置服务返回码
	 * 
	 * @param respCode
	 *            服务返回码
	 */
	public void setRespCode(String respCode) {
		super.setHeadValue(IConstant.RESPONSE_CODE, respCode);
	}

	/**
	 * 获取错误信息
	 * 
	 * @return 错误信息
	 */
	public String getRespMsg() {
		return (String) super.getHeadValue(IConstant.RESPONSE_MESSAGE);
	}

	/**
	 * 设置错误信息
	 * 
	 * @param respMsg
	 *            错误信息
	 */
	public void setRespMsg(String respMsg) {
		super.setHeadValue(IConstant.RESPONSE_MESSAGE, respMsg);
	}

	/**
	 * 获取初始请求序号，为对应请求对象，使用相同的键
	 * 
	 * @return 初始请求序号
	 */
	public String getReqSeqNo() {
		return (String) super.getHeadValue(IConstant.REQUEST_SEQUENCE_NO);
	}

	/**
	 * 设置初始请求序号，为对应请求对象，使用相同的键
	 * 
	 * @param reqSeqNo
	 *            初始请求序号
	 */
	public void setReqSeqNo(String reqSeqNo) {
		super.setHeadValue(IConstant.REQUEST_SEQUENCE_NO, reqSeqNo);
	}

}
