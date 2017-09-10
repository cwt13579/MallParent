package com.iec.core.app.middleware.dubbo.service;
/**
 * 服务调用常量类
 * 
 * @author SCRCU
 * @version 1.0.0
 */
public interface IConstant {
	/**
	 * 应用ID,电子银行可以使用统一的一个名称
	 */
	String APP_ID = "appId";
	/**
	 * 渠道Id, 用于优先级控制和通道关闭，编码按数据架构代码标准
	 */
	String CHANNEL_ID = "chnlId";
	/**
	 * 服务唯一标识
	 */
	String SERVICE_ID = "servcId";
	/**
	 * 用户标识
	 */
	String USER_ID = "userId";
	/**
	 * 用户认证等级
	 */
	String USER_CERT_LEVEL = "userCertLevel";
	/**
	 * 发起服务调用的服务器IP
	 */
	String HOST_IP = "hostIp";
	/**
	 * 机构编号
	 */
	String BRANCH_ID = "branchId";
	/**
	 * 服务调用时间（字符串：YYYYMMDDhhmmss）
	 */
	String REQUEST_TIME = "reqTime";
	/**
	 * 业务跟踪号,格式为[应用系统标识符4位][日期标识符，YYMMDD][流水序号，数字最大10位] 共20位
	 */
	String BUSINESS_TRACK_NO = "bizTrackNo";
	/**
	 * 请求序号,格式为[应用系统标识符4位][日期标识符，YYMMDD][流水序号，数字最大10位] 共20位
	 */
	String REQUEST_SEQUENCE_NO = "reqSeqNo";
	/**
	 * 原始交易ID, 与该次调用关联的上次调用的ReqId
	 */
	String ORIGINAL_SEQUENCE_NO = "orgnlSeqNo";
	/**
	 * 终端设备编号，如ATM、POS、手机等
	 */
	String TERMINATOR_ID = "termnId";
	/**
	 * 分页时的记录数
	 */
	String PAGE_SIZE = "pageSize";
	/**
	 * 分页时的查询起始记录数,计算方式为（页码-1）*每页大小
	 */
	String OFFSET = "offset";
	/**
	 * 应用签名，使用MD5的变种算法签名，签名信息为：AppId_ReqTime, 密钥使用业务系统私有密钥，可以通过配置激活
	 */
	String APP_SIGN = "appSign";
	/**
	 * body中业务数据(不包含标签)的MD5变种算法签名，body签名使用SOA平台统一密钥，可以通过配置激活(与appSign独立配置)
	 */
	String DATA_SIGN = "dataSign";
	/**
	 * 柜员、虚拟柜员号
	 */
	String OPER_ID = "operId";
	/**
	 * 保留字段1
	 */
	String RESERVE_1 = "resv1";
	/**
	 * 保留字段2
	 */
	String RESERVE_2 = "resv1";
	/**
	 * 服务响应码
	 */
	String RESPONSE_CODE = "respCode";
	/**
	 * 响应信息,有错则必填
	 */
	String RESPONSE_MESSAGE = "respMsg";
	/**
	 * 服务返回时间
	 */
	String RETURN_TIME = "returnTime";
	/**
	 * 服务端流水号
	 */
	String SERVICE_SEQUENCE_NO = "servcSeqNo";
	/**
	 * 返回总记录数
	 */
	String RECORD_COUNT = "recordCount";
	/**
	 * 服务端IP地址
	 */
	String SERVER_IP = "serverIp";
	/**
	 * 成功响应码
	 */
	String SUCCESS_RESPONSE_CODE = "0000000000";
}
