package com.iec.core.app.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author xyp 
 * @version create time：2013-5-28 下午01:08:29 
 * 
 */
public class PricePeriodMapper {
	private static final Map<String, Integer> map = new HashMap<String, Integer>();
//	week:1 ,month:2 ,quarterly:3,halfyear:4,year:5
	static{
		map.put("week", 1);
		map.put("month", 2);
		map.put("quarterly", 3);
		map.put("halfyear", 4);
		map.put("year", 5);
	}
	public static final  Integer getValue(String key){
		return  map.get(key) ;
	}
}
