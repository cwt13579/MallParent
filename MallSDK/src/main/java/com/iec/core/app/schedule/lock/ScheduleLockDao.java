package com.iec.core.app.schedule.lock;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iec.core.app.base.BeanContext;

public class ScheduleLockDao extends JdbcDaoSupport {
	static Logger logger = Logger.getLogger("task");

	public ScheduleLockDao(){
		
		JdbcTemplate baseJdbcTemplate = BeanContext.getBean("baseJdbcTemplate",JdbcTemplate.class);
    	setBaseJdbcTemplate(baseJdbcTemplate);
	}
	
	public ScheduleLock getScheduleLock(Long lock_key) {
		
		try{
			//logger.debug("ScheduleLockDao.getScheduleLock,lock_key="+lock_key);
	  
		String sql = "select lock_key,schedule_key,machine_ip,version_no,heartbeat_frequency,time_out,create_time,modify_time,lock_desc from t_schedule_lock where lock_key= "+lock_key;

		ScheduleLock scheduleLock = this.getJdbcTemplate().queryForObject(sql,new ScheduleLock());

		return scheduleLock;
		
		}catch(Exception e){
			logger.error("ScheduleLockDao.getScheduleLock Exception,lock_key="+lock_key, e);
		}

		return null;
	}

	public ScheduleLock createScheduleLock(final Long lock_key) throws ScheduleLockException {
		ScheduleLock scheduleLock = null;
		try{
			//logger.debug("ScheduleLockDao.createScheduleLock,lock_key="+lock_key);
		
		final java.sql.Timestamp timestamp = new java.sql.Timestamp(new Date().getTime());
		
		scheduleLock = new ScheduleLock(ScheduleLockEnum.getEnumByLockKey(lock_key));
		
		String sql = "insert into t_schedule_lock (lock_key,schedule_key,machine_ip,version_no,heartbeat_frequency,time_out,create_time,modify_time,lock_desc) values(?,?,?,?,?,?,?,?,?)";

		this.getJdbcTemplate().update(
				sql,scheduleLock.getLock_key(), 
				scheduleLock.getSchedule_key(),
				scheduleLock.getNewMachine_ip(),
				scheduleLock.getVersion_no(),
				scheduleLock.getHeartbeat_frequency(),
				scheduleLock.getTime_out(),
	            timestamp, 
	            timestamp,
	            scheduleLock.getLock_desc());
        
		
		return scheduleLock;
		
		}catch(Exception e){
			logger.error("ScheduleLockDao.createScheduleLock Exception,"+scheduleLock,e);
			return null;
		}
	}
	
	public boolean updateScheduleLock(Long lock_key) throws ScheduleLockException {
		ScheduleLock lock = getScheduleLock(lock_key);
		return updateScheduleLock(lock);
	}
	
	public boolean updateScheduleLock(ScheduleLock lock) throws ScheduleLockException {

		//logger.debug("ScheduleLockDao.updateScheduleLock,"+lock);
		
		try{
			//要加悲观锁，用版本号来控制
			String sql = "update t_schedule_lock set machine_ip=?,version_no=?,modify_time=? where lock_key=? and version_no=?";

			int ret = this.getJdbcTemplate().update(sql, lock.getNewMachine_ip(),lock.next_version_no(),new java.sql.Timestamp(new Date().getTime()),lock.getLock_key(),lock.getVersion_no());
	
			if(ret<1){
				throw new ScheduleLockException("updateScheduleLock failure,sql="+sql);
			}

			return true;
			
		}catch(Exception e){
			logger.error("ScheduleLockDao.updateScheduleLock Exception,"+lock,e);
		}
		return false;
	}
	
	@Autowired
    public void setBaseJdbcTemplate(JdbcTemplate b2bJdbcTemplate) {
    	setJdbcTemplate(b2bJdbcTemplate);
    }
	
	
}
