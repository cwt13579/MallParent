package com.iec.core.app.utils;

import java.util.HashMap;
import java.util.Map;

public class CalDateMapUtils {
	
	private static Map<Integer,Double> map = new HashMap<Integer,Double>();
	/*订购周期(1:一周;2:一个月;3:一季度;4:半年;5:一年)*/
	static {
		map.put(0,0.00);
		map.put(1,0.25);
		map.put(2,1.00);
		map.put(3,3.00);
		map.put(4,6.00);
		map.put(5,12.00);
		
	}
	public static Map<Integer, Double> getMap() {
		return map;
	}
	public static void setMap(Map<Integer, Double> map) {
		CalDateMapUtils.map = map;
	}
	
	

}
