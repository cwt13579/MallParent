package com.iec.core.app.schedule.lock;

import java.util.concurrent.atomic.AtomicInteger;


public class Lock {

	private static boolean Locked = false;
	private static AtomicInteger Update_Fail_Count = new AtomicInteger();
	
	public static void setLocked(boolean status){
		Locked = status;
	}
	public static boolean isLocked(){
		return Locked;
	}

	public static void updateFail(Long frequency, Long time_out) {
			long a = time_out/frequency-1;
			if(Update_Fail_Count.incrementAndGet() > a || Update_Fail_Count.get()>5){
				setLocked(false);
			}
		}
}
