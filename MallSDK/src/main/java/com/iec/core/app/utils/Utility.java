package com.iec.core.app.utils;

/**
 * 系统工具类
 * @author LUBANG713
 * @date 2014-3-24
 */
public class Utility {

	public static boolean isWindowsOS() {

		String osName = System.getProperty("os.name");
		if (osName != null && osName.length() > 0
				&& osName.toUpperCase().indexOf("WINDOWS") >= 0) {
			return true;
		} else {
			return false;
		}

	}
}
