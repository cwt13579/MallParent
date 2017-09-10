package com.iec.core.app.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 保存url
 * @author lenovo
 *
 */
public class UrlUtils {

	
	public static final String ClearCookie=".iecmall.com";
	public static final String bankurl="b2c.iecmall.com:8083";
	public static final String COMPANY_URL="http://b2c.iecmall.com:8083/MallWeb/shop/index.html?id=";
	
	
	public static String getBasePath(HttpServletRequest request){
		String basePath = request.getScheme() + "://" + request.getServerName();
		
		int port = request.getServerPort();
		if(port!=80){
			basePath +=":" + port;
		}
		
		basePath += request.getContextPath();
		
		return basePath;
	}
}
