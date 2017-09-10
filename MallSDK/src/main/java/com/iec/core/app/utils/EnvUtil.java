package com.iec.core.app.utils;

import java.util.Properties;

/**
 * 解决根据不同的环境获取不同的配置项
 * 
 * @author LUBANG713
 * @date 2014-3-29
 */
public class EnvUtil {
	
	public static final String ENV_VARIALE_NAME = "conf.env";
	
	public static final String ENV_LOCAL = "local";
	public static final String ENV_DEV = "dev";
	public static final String ENV_UAT = "uat";
	public static final String ENV_STAG = "stag";
	public static final String ENV_PRE_PRD = "pre_prd";//预生产环境
	public static final String ENV_PRD = "prd";

	public static boolean isLocalEnv() {
		return ENV_LOCAL == getCurrentEnv();
	}

	public static boolean isDEVEnv() {
		return ENV_DEV == getCurrentEnv();
	}

	public static boolean isUATEnv() {
		return ENV_UAT == getCurrentEnv();
	}

	public static boolean isSTAGEnv() {
		return ENV_STAG == getCurrentEnv();
	}
	public static boolean isPREPRDEnv() {
		return ENV_PRE_PRD == getCurrentEnv();
	}
	public static boolean isPRDEnv() {
		return ENV_PRD == getCurrentEnv();
	}

	public static String getCurrentEnv() {
		Properties sysProp = System.getProperties();
		return  sysProp.getProperty(ENV_VARIALE_NAME);
	}
}
