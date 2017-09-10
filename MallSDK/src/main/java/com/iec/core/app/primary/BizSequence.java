package com.iec.core.app.primary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class BizSequence implements  RowMapper<BizSequence>{  
	  
	
    public BizSequence(IDEnum idEnum) {
    	setBIZ_TYPE(idEnum.getBIZ_TYPE());
		setMINVAL(idEnum.getMINVAL());
		setMAXVAL(idEnum.getMAXVAL());
		setINCRET(idEnum.getINCRET());
		setBIZ_DESC(idEnum.getBIZ_DESC());
	}
    
    public BizSequence(){
    	
    }
	public BizSequence mapRow(final ResultSet rs, final int rowNum) {  
	  
    	BizSequence bizSeq = new BizSequence();
		try {
			if(rs!=null){
				bizSeq.setBIZ_TYPE(rs.getInt("BIZ_TYPE"));
				bizSeq.setMINVAL(rs.getLong("MINVAL"));
				bizSeq.setCURVAL(rs.getLong("CURVAL"));
				bizSeq.setMAXVAL(rs.getLong("MAXVAL"));
				bizSeq.setINCRET(rs.getInt("INCRET"));
				bizSeq.setBIZ_DESC(rs.getString("BIZ_DESC"));
				bizSeq.setMODIFY_TIME(rs.getDate("MODIFY_TIME"));
				bizSeq.setCREATE_TIME(rs.getDate("CREATE_TIME"));
			return bizSeq;  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
    }  
    
	  private Integer BIZ_TYPE;
	  private Long MINVAL = 100000000L;
	  private Long CURVAL ;
	  private Long MAXVAL = 999999999L;
	  private Integer INCRET = 100;
	  private Date CREATE_TIME = new Date();
	  private Date MODIFY_TIME = new Date();
	  private String BIZ_DESC;
	  
	public Integer getBIZ_TYPE() {
		return BIZ_TYPE;
	}
	public void setBIZ_TYPE(Integer bIZ_TYPE) {
		BIZ_TYPE = bIZ_TYPE;
	}
	public Long getMINVAL() {
		return MINVAL;
	}
	public void setMINVAL(Long mINVAL) {
		MINVAL = mINVAL;
	}
	
	public Long getCURVAL() {
		return CURVAL;
	}
	public void setCURVAL(Long cURVAL) {
		CURVAL = cURVAL;
	}
	public Long getMAXVAL() {
		return MAXVAL;
	}
	public void setMAXVAL(Long mAXVAL) {
		MAXVAL = mAXVAL;
	}
	public Integer getINCRET() {
		return INCRET;
	}
	public void setINCRET(Integer iNCRET) {
		INCRET = iNCRET;
	}
	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public Date getMODIFY_TIME() {
		return MODIFY_TIME;
	}
	public void setMODIFY_TIME(Date mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
	}
	public String getBIZ_DESC() {
		return BIZ_DESC;
	}
	public void setBIZ_DESC(String bIZ_DESC) {
		BIZ_DESC = bIZ_DESC;
	}
	@Override
	public String toString() {
		return "BizSequence [BIZ_TYPE=" + BIZ_TYPE + ", MINVAL=" + MINVAL
				+ ", CURVAL=" + CURVAL + ", MAXVAL=" + MAXVAL + ", INCRET="
				+ INCRET + ", CREATE_TIME=" + CREATE_TIME + ", MODIFY_TIME="
				+ MODIFY_TIME + ", BIZ_DESC=" + BIZ_DESC + "]";
	}
}