package com.iec.core.app.schedule;

import org.apache.log4j.Logger;

import com.iec.core.app.schedule.lock.Lock;

/**
 * 调度抽象类，（公共逻辑放这里处理）
 * @author LUBANG713
 * @date 2014-3-21
 */
public abstract class AbsJobScheduler implements JobScheduler {	

	public static Logger logger = Logger.getLogger(AbsJobScheduler.class);
	public final void execute(){

		//只有取到锁的机器才可以执行任务调度
		if(Lock.isLocked()){
			schedule();
		}
	}

	protected abstract void schedule();
}
