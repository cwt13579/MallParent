package com.iec.core.app.primary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * 主键实体
 * @author LUBANG713
 * @date 2014-2-19
 */
public class Primary { 

	static Logger log = Logger.getLogger(Primary.class);

	private BizSequence bizSeq;
		
	private AtomicLong CurSeq= new AtomicLong(0);
	private AtomicLong NextSeq= new AtomicLong(0);
		
	private AtomicLong SeqKeeper= new AtomicLong(0);
	private int SwitchVal = 10;
		
		private Integer biz_type ;
		
		public Primary(Integer biz_type) throws BizSeqException{
			this.biz_type = biz_type;
			bizSeq = getBizSequence();
			if(bizSeq == null){
				bizSeq = createBizSequence();
			}else{
				bizSeq = updateBizSequence();
			}
			if(bizSeq ==null){
				throw new BizSeqException("Primary Exception");
			}
			
			SeqKeeper.set(bizSeq.getINCRET());
			CurSeq.set(bizSeq.getCURVAL());
		}
		
		
		public BizSequence getBizSequence() {
			return new BizSequenceDao().getBizSequence(biz_type);
		}

		public BizSequence createBizSequence() throws BizSeqException {
			return new BizSequenceDao().createBizSequence(biz_type);
		}
		
		public BizSequence updateBizSequence() {
			return new BizSequenceDao().updateBizSequence(biz_type);
		}
		
		/**
		 * 取序列，并自减
		 * @return
		 */
		public Long decrementAndGet() {
			SeqKeeper.decrementAndGet();
			return CurSeq.get()-SeqKeeper.get();
		}

		/**
		 * 切换到下一个序列
		 * @return
		 */
		public Long nextAndGet() {
			getCurSeq().set(NextSeq.get());
			getNextSeq().set(0);
			SeqKeeper.set(bizSeq.getINCRET());
			
			return decrementAndGet();
		}
		
		/**
		 * 当目前容器的值低于某一阀值时,并且下一个容器为空时，需要预备装载下一个容器。
		 * @return
		 */
		public boolean isSwitching() {
			return SeqKeeper.get() < SwitchVal && NextSeq.get()<=0;
		}

		/**
		 * 装载下一个容器。
		 */
		public void loadNextSeqKeeper() {
			bizSeq = updateBizSequence();
			
			//可能会有并发问题，不一定能更新成功。
			if(bizSeq != null){
				setNextSeq(new AtomicLong(bizSeq.getCURVAL()));
				log.info("IDMaker.updateBizSequence:"+bizSeq);
			}
			
		}
		
		public Integer getBiz_type() {
			return biz_type;
		}
		public void setBiz_type(Integer biz_type) {
			this.biz_type = biz_type;
		}
		
		public AtomicLong getCurSeq() {
			return CurSeq;
		}
		public void setCurSeq(AtomicLong CurSeq) {
			this.CurSeq = CurSeq;
		}
		public AtomicLong getNextSeq() {
			return NextSeq;
		}
		public void setNextSeq(AtomicLong NextSeq) {
			this.NextSeq = NextSeq;
		}
		public AtomicLong getSeqKeeper() {
			return SeqKeeper;
		}
		public void setSeqKeeper(AtomicLong seqKeeper) {
			this.SeqKeeper = seqKeeper;
		}	


	    @Override
		public String toString() {
			return "Primary [bizSeq=" + bizSeq + ", CurSeq=" + CurSeq
					+ ", NextSeq=" + NextSeq + ", SeqKeeper=" + SeqKeeper
					+ ", biz_type=" + biz_type + "]";
		}
	    
	    public static int getYYMMDD() {
	    	
	    	SimpleDateFormat sy1=new SimpleDateFormat("yyMMdd");

			String dateFormat=sy1.format(new Date());
			return Integer.parseInt(dateFormat);
			
//			Calendar now = Calendar.getInstance();  
//	        String yymmdd = "";
//	        yymmdd+=now.get(Calendar.YEAR);  
//	        yymmdd=yymmdd.substring(yymmdd.length()-2,yymmdd.length());
//	        
//	        int mm =(now.get(Calendar.MONTH)+1);  
//	        yymmdd+=mm>=10?mm:"0"+mm;
//	        
//	        int dd = now.get(Calendar.DAY_OF_MONTH); 
//	        yymmdd+=dd>=10?dd:"0"+dd;
//	        
//	        return Integer.parseInt(yymmdd);
	        
		}     
}	
		

