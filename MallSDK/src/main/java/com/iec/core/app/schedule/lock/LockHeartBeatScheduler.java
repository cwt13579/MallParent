package com.iec.core.app.schedule.lock;

import org.apache.log4j.Logger;

import com.iec.core.app.schedule.JobScheduler;
import com.iec.core.app.utils.Utility;

/**
 * 心跳任务调度锁
 * @author LUBANG713
 * @date 2014-3-22
 */
public class LockHeartBeatScheduler implements JobScheduler {
	
	static Logger logger = Logger.getLogger("task");

	private Long lockKey;
	
	public LockHeartBeatScheduler(Long lockKey){
		this.lockKey = lockKey;
	}
	public LockHeartBeatScheduler(){
	}
	
	@Override
	public void execute() { 

		if(Utility.isWindowsOS()){
			Lock.setLocked(true);
			return ;//本地开发不设锁
		}

		try {

			ScheduleLockDao dao = new ScheduleLockDao();
			ScheduleLock lock = dao.getScheduleLock(lockKey);
			
			//数据库里还没有锁
			if (lock == null) {
				logger.info(" going to createScheduleLock!!!!");
				ScheduleLock lock_ = dao.createScheduleLock(lockKey);
				if(lock_!=null){
					Lock.setLocked(true);
					return ;
				}
				
			//锁已经过期，去获取锁，成功后将持有锁
			}else if(lock.isTimeOut()){
				logger.info(lock+" is timeout!!!!,this machine going to get lock...");
				
				boolean result = dao.updateScheduleLock(lock);
				if(result){
					Lock.setLocked(true);
				}
				
				logger.info(lock+" ,get lock is :"+result);
			
				
			//如果已经取得锁，也要去保持锁的状态
			}else if(Lock.isLocked()){
				logger.info("machine_ip:"+lock.getMachine_ip()+" is heartbeating ....");
				boolean result = dao.updateScheduleLock(lock);
				if(!result){
					Lock.updateFail(lock.getHeartbeat_frequency(),lock.getTime_out());
				}
			}
			
			
		} catch (ScheduleLockException e) {
			logger.error("schedule ScheduleLockException", e);
		}
	}

}
