package com.iec.core.app.utils;

public class SystemCode {
	public static final int DEV_USER_STATUS_UNVERIFY = 1 ;
	public static final int DEV_USER_STATUS_VERIFIED = 2 ;
	public static final int DEV_USER_STATUS_DELETED = 9 ;
	
//	审计状态（1：未提交审核；2：审核中；3：审核通过；5：审核不通过）
	public static final int COMPANY_AUDIT_STATUS_UNAUDIT = 1 ;
	public static final int COMPANY_AUDIT_STATUS_PEDDING = 2 ;
	public static final int COMPANY_AUDIT_STATUS_AUDITED = 3 ;
	public static final int COMPANY_AUDIT_STATUS_FAILURE = 5 ;
	
	public static final String COMPANY_AUDIT_STATUS_NAME = "status_value" ;
	
}
