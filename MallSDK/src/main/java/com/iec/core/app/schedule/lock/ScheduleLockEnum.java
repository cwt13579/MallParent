package com.iec.core.app.schedule.lock;


/**
 * 任务调度锁枚举类
 * @author LUBANG713
 * @date 2014-3-22
 */
public enum ScheduleLockEnum{
	
	O2OAPP(10000L,10000L,1L,60L,300L,"口袋社区前台任务调度"),
	UNKNOW(0L,0L,1L,60L,300L,"未知");
	
	
	private Long lock_key;// 锁KEY 主键
	private Long schedule_key;// 调度任务KEY 唯一索引
	private Long version_no;// 版本号，每次拿到锁后，要更新版本号
	private Long heartbeat_frequency;// 心跳频率,单位秒
	private Long time_out;// 超时时间,单位秒
	private String lock_desc;// 描述
	
	ScheduleLockEnum(Long lock_key,
			Long schedule_key,
			Long version_no,
			Long heartbeat_frequency,
			Long time_out,
			String lock_desc){
		
		this.lock_key = lock_key;
		this.schedule_key = schedule_key;
		this.version_no = version_no;
		this.heartbeat_frequency = heartbeat_frequency;
		this.time_out = time_out;
		this.lock_desc = lock_desc;
	}
	
	public Long getLock_key() {
		return lock_key;
	}

	public void setLock_key(Long lock_key) {
		this.lock_key = lock_key;
	}

	public Long getSchedule_key() {
		return schedule_key;
	}

	public void setSchedule_key(Long schedule_key) {
		this.schedule_key = schedule_key;
	}

	public Long getVersion_no() {
		return version_no;
	}

	public void setVersion_no(Long version_no) {
		this.version_no = version_no;
	}

	public Long getHeartbeat_frequency() {
		return heartbeat_frequency;
	}

	public void setHeartbeat_frequency(Long heartbeat_frequency) {
		this.heartbeat_frequency = heartbeat_frequency;
	}

	public Long getTime_out() {
		return time_out;
	}

	public void setTime_out(Long time_out) {
		this.time_out = time_out;
	}

	public String getLock_desc() {
		return lock_desc;
	}

	public void setLock_desc(String lock_desc) {
		this.lock_desc = lock_desc;
	}

	public static ScheduleLockEnum getEnumByLockKey(Long lock_key){
		for (ScheduleLockEnum ptEnum : ScheduleLockEnum.values()) {
			if(ptEnum.lock_key.longValue()==lock_key.longValue())
				return ptEnum;
		}
		return UNKNOW;
	}
}
