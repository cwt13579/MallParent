package com.iec.core.app.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放已经生成静态页的url与path
 * 
 * @author ASUS
 * 
 */
public class ToHtmlRequestMap {

    private static Map<String, SysHtml> toHtmlMap = new HashMap<String, SysHtml>();

    public static Map<String, SysHtml> getToHtmlMap() {
	return toHtmlMap;
    }

    public static void put(String key, SysHtml value) {
	ToHtmlRequestMap.toHtmlMap.put(key, value);
    }

    public static SysHtml get(String key) {
	return ToHtmlRequestMap.toHtmlMap.get(key);
    }

}
