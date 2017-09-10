package com.iec.core.app.schedule.lock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.iec.core.app.utils.SystemIp;

/**
 * 任务调度锁实体
 * 
 * @author LUBANG713
 * @date 2014-3-22
 */
public class ScheduleLock implements  RowMapper<ScheduleLock>{ 

	private Long lock_key;// 锁KEY
	private Long schedule_key;// 调度任务KEY
	private String machine_ip;// 机器ip
	private Long version_no;// 版本号，每次拿到锁后，要更新版本号
	private Long heartbeat_frequency;// 心跳频率,单位秒
	private Long time_out;// 超时时间,单位秒
	private Date create_time;// 添加时间
	private Date modify_time;// 最后修改时间
	private String lock_desc;// 描述
	
	
	public ScheduleLock(){
		
	}
	
	 public ScheduleLock(ScheduleLockEnum lockEnum) {
	    	setLock_key(lockEnum.getLock_key());
	    	setSchedule_key(lockEnum.getSchedule_key());
	    	setVersion_no(lockEnum.getVersion_no());
	    	setHeartbeat_frequency(lockEnum.getHeartbeat_frequency());
	    	setTime_out(lockEnum.getTime_out());
	    	setLock_desc(lockEnum.getLock_desc());
		}
	 
	public ScheduleLock mapRow(final ResultSet rs, final int rowNum) {  
		  
		ScheduleLock lock = null;
		try {
			if(rs!=null){
				lock = new ScheduleLock();
				lock.setLock_key(rs.getLong("lock_key"));
				lock.setSchedule_key(rs.getLong("schedule_key"));
				lock.setMachine_ip(rs.getString("machine_ip"));
				lock.setHeartbeat_frequency(rs.getLong("heartbeat_frequency"));
				lock.setVersion_no(rs.getLong("version_no"));
				lock.setTime_out(rs.getLong("time_out"));
				lock.setCreate_time(rs.getTimestamp("create_time"));
				lock.setModify_time(rs.getTimestamp("modify_time"));
				lock.setLock_desc(rs.getString("lock_desc"));
				return lock;  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
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
	public String getMachine_ip() {
		return machine_ip;
	}
	public String getNewMachine_ip() {
		return SystemIp.getHostIp();
	}

	public void setMachine_ip(String machine_ip) {
		this.machine_ip = machine_ip;
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
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public String getLock_desc() {
		return lock_desc;
	}
	public void setLock_desc(String lock_desc) {
		this.lock_desc = lock_desc;
	}
	
	public Long next_version_no(){
		return version_no+1;
	}
	
	public boolean isTimeOut(){
		return (System.currentTimeMillis() - modify_time.getTime()) /1000 > time_out;
		
	}
	
	/**
	 * 是否已经有机器锁定调度任务
	 * @return
	 */
	public boolean isLocked(){
		return !isTimeOut();//锁的心跳还在
	}
	
	@Override
	public String toString() {
		return "ScheduleLock [lock_key=" + lock_key + ", schedule_key="
				+ schedule_key + ", machine_ip=" + machine_ip + ", version_no="
				+ version_no + ", heartbeat_frequency=" + heartbeat_frequency
				+ ", time_out=" + time_out + ", create_time=" + create_time
				+ ", modify_time=" + modify_time + ", lock_desc=" + lock_desc
				+ "]";
	}
	
	

}
