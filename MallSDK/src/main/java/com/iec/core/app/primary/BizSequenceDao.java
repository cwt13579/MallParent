package com.iec.core.app.primary;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iec.core.app.base.BeanContext;

public class BizSequenceDao extends JdbcDaoSupport {
	static Logger log = Logger.getLogger(BizSequenceDao.class);
	private BizSequence bizSeq;

	public BizSequenceDao(){
		JdbcTemplate baseJdbcTemplate = BeanContext.getBean("baseJdbcTemplate",JdbcTemplate.class);
    	setBaseJdbcTemplate(baseJdbcTemplate);
	}
	
	public BizSequence getBizSequence(Integer biz_type) {
		
		try{
		log.info("Primary.getMaxSeq,biz_type="+biz_type);
	
		String sql = "select BIZ_TYPE,MINVAL,CURVAL,MAXVAL,INCRET,CREATE_TIME,MODIFY_TIME,BIZ_DESC from T_BIZ_SEQUENCE where BIZ_TYPE= "+biz_type;

		BizSequence bizSeq = this.getJdbcTemplate().queryForObject(sql,new BizSequence());

		return bizSeq;
		
		}catch(Exception e){
			log.error("Primary.getMaxSeq Exception,biz_type="+biz_type, e);
		}

		return null;
	}

	public BizSequence createBizSequence(final Integer biz_type) throws BizSeqException {
		
		try{
		log.info("Primary.createBizSequence,biz_type="+biz_type);
		
		final java.sql.Timestamp timestamp = new java.sql.Timestamp(new Date().getTime());
	
		bizSeq = new BizSequence(IDEnum.getEnumType(biz_type));
		
		String sql = "insert into T_BIZ_SEQUENCE (BIZ_TYPE,MINVAL,CURVAL,MAXVAL,INCRET,CREATE_TIME,MODIFY_TIME,BIZ_DESC) values(?,?,?,?,?,?,?,?)";

		this.getJdbcTemplate().update(
				sql,biz_type, 
				bizSeq.getMINVAL(), 
				(bizSeq.getMINVAL()+bizSeq.getINCRET()),
	            bizSeq.getMAXVAL(), 
	            bizSeq.getINCRET(), 
	            timestamp, 
	            timestamp,
	            bizSeq.getBIZ_DESC());
        
		bizSeq.setCURVAL(bizSeq.getMINVAL()+bizSeq.getINCRET());
		
		return bizSeq;
		
		}catch(Exception e){
			throw new BizSeqException("Primary.createBizSequence Exception,biz_type="+biz_type,e);
		}
	}
	
	public BizSequence updateBizSequence(Integer biz_type) {
		long t = System.currentTimeMillis();
		log.info("Primary.updateBizSequence,biz_type="+biz_type);
		Long cur_val  = null;

		try{
			//要加悲观锁，以防取出最大值后，被其他客户端更改。
			String sql = "update T_BIZ_SEQUENCE set CURVAL=?,INCRET=?,MODIFY_TIME=? where BIZ_TYPE=? and CURVAL=?";
			
			bizSeq = getBizSequence(biz_type);
			
			if(bizSeq.getCURVAL()+bizSeq.getINCRET() > bizSeq.getMAXVAL()){
				cur_val = bizSeq.getMINVAL() + bizSeq.getINCRET();
			}else{
				cur_val = bizSeq.getCURVAL() + bizSeq.getINCRET();
			}
			
			int ret = this.getJdbcTemplate().update(sql, cur_val,bizSeq.getINCRET(),new java.sql.Timestamp(new Date().getTime()),biz_type,bizSeq.getCURVAL());
			
			long endT = System.currentTimeMillis();
			log.info("Primary.updateBizSequence cost:"+(endT-t));

			if(ret<1){
				throw new BizSeqException("Primary.updateBizSequence failure,sql="+sql);
			}
			
			bizSeq.setCURVAL(cur_val);
			return bizSeq;
			
		}catch(Exception e){
			log.error("Primary.updateBizSequence Exception,biz_type="+biz_type,e);
		}
		return null;
	}
	
	@Autowired
    public void setBaseJdbcTemplate(JdbcTemplate b2bJdbcTemplate) {
    	setJdbcTemplate(b2bJdbcTemplate);
    }
	
	
}
