package com.iec.core.app.middleware.dubbo.service;

import java.util.Date;

/**
 * 服务请求对象 服务调用使用的参数
 * 
 * @author SCRCU
 * @version 1.0.0
 * @see com.scrcu.ebank.common.service.ServiceBase
 */
public class ServiceRequest extends ServiceBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1742453304978731408L;

	/**
	 * 获取渠道号
	 * 
	 * @return 渠道号
	 */
	public String getChannelId() {
		return (String) super.getHeadValue(IConstant.CHANNEL_ID);
	}

	/**
	 * 设置渠道号
	 * 
	 * @param channelId
	 *            渠道号
	 */
	public void setChannelId(String channelId) {
		super.setHeadValue(IConstant.CHANNEL_ID, channelId);
	}

	/**
	 * 获取服务标识
	 * 
	 * @return 服务标识
	 */
	public String getServiceId() {
		return (String) super.getHeadValue(IConstant.SERVICE_ID);
	}

	/**
	 * 设置服务标识
	 * 
	 * @param serviceId
	 *            服务标识
	 */
	public void setServiceId(String serviceId) {
		super.setHeadValue(IConstant.SERVICE_ID, serviceId);
	}

	/**
	 * 获取用户标识
	 * 
	 * @return 用户标识
	 */
	public String getUserId() {
		return (String) super.getHeadValue(IConstant.USER_ID);
	}

	/**
	 * 设置服务标识
	 * 
	 * @param userId
	 *            服务标识
	 */
	public void setUserId(String userId) {
		super.setHeadValue(IConstant.USER_ID, userId);
	}

	/**
	 * 获取请求序列号
	 * 
	 * @return
	 */
	public String getReqSeqNo() {
		return (String) super.getHeadValue(IConstant.REQUEST_SEQUENCE_NO);
	}

	/**
	 * 设置请求序列号
	 * 
	 * @param reqSeqNo
	 *            请求序列号
	 */
	public void setReqSeqNo(String reqSeqNo) {
		super.setHeadValue(IConstant.REQUEST_SEQUENCE_NO, reqSeqNo);
	}

	/**
	 * 获取服务发起请求时间
	 * 
	 * @return 服务发起请求时间
	 */
	public Date getRequestTime() {
		return (Date) super.getHeadValue(IConstant.REQUEST_TIME);
	}

	/**
	 * 设置服务发起请求时间
	 * 
	 * @param reqTime
	 *            服务发起请求时间
	 */
	public void setRequestTime(Date reqTime) {
		super.setHeadValue(IConstant.REQUEST_TIME, reqTime);
	}

	/**
	 * 获取业务跟踪号
	 * 
	 * @return 业务跟踪号
	 */
	public String getBizTrackNo() {
		return (String) super.getHeadValue(IConstant.BUSINESS_TRACK_NO);
	}

	/**
	 * 设置业务跟踪号
	 * 
	 * @param bizTrackNo
	 *            业务跟踪号
	 */
	public void setBizTrackNo(String bizTrackNo) {
		super.setHeadValue(IConstant.BUSINESS_TRACK_NO, bizTrackNo);
	}

}
