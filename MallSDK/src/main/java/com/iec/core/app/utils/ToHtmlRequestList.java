package com.iec.core.app.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 静态化请求集合
 * @author LiuSQ
 *
 */
public class ToHtmlRequestList {

	private static List<String>  trl = new ArrayList<String>();

	public static List<String> getTrl() {
		return trl;
	}

	public static void setTrl(List<String> trl) {
		ToHtmlRequestList.trl = trl;
	}
	
	public static void addRequest(String request){
		getTrl().add(request);
	}
	
	public static boolean contains(String request){
		return getTrl().contains(request);
	}
	
}
