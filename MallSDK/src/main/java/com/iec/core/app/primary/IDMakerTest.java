package com.iec.core.app.primary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.iec.core.app.utils.DateUtil;

public class IDMakerTest {

	/**
	 * 445027140423104340001785
	 * @param args
	 */
	static long t = System.currentTimeMillis();
	public static void main(String[] args) {
		
		long primaryKey = IDMaker.getID(IDEnum.SHOP.getBIZ_TYPE(), "254142342785");
		
//		System.out.println(primaryKey);
		primaryKey = IDMaker.getID(IDEnum.HISCOMMUNITY.getBIZ_TYPE(), "254142342785");
		System.out.println(primaryKey);
		
		primaryKey = IDMaker.getID(IDEnum.SHOP.getBIZ_TYPE());
		System.out.println(primaryKey);

		primaryKey = IDMaker.getID(IDEnum.SHOP.getBIZ_TYPE(),false);
		System.out.println(primaryKey);
		
//		System.out.println(primaryKey);
		
		long id = 9014010192345678900L;
		
//		for (int i = 0; i < 10; i++) {
//			new IDMakerTest().new PrimaryThread().start();
//		} 
//		
		
		
		
	}
	
	static Map<Long,Long> map = Collections.synchronizedMap(new HashMap<Long,Long>());
	
	class PrimaryThread extends Thread{
		
		public void run(){
			for (int i = 0; i < 10000;i++) {
			  long primaryKey = IDMaker.getID(IDEnum.SHOP.getBIZ_TYPE(), "254142342785");
			  System.out.println("445027"+DateUtil.getTodayDate2()+primaryKey);
				
				if(map.containsKey(primaryKey)){
					System.out.println(primaryKey+"------------------------------");
					System.exit(-1);
				}
				map.put(primaryKey, primaryKey);
			}
			
			System.out.println(">>>>>size:"+map.size());
			long endT = System.currentTimeMillis();
			
			System.out.println("===========================cost:"+(endT-t));
		}
	}

}
