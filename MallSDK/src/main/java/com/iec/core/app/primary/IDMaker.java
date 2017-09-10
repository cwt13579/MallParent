package com.iec.core.app.primary;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 * 业务主键生成器
 * 在本地做二级缓存，通过分段的方式分配主键
 * @author LUBANG713
 * @date 2014-2-19
 */
public final class IDMaker {

	private static Logger log = Logger.getLogger(IDMaker.class);
	private static Map<Integer,Primary> container = new HashMap<Integer,Primary>();

	
	public static void reload(Integer bizType){
		container.put(bizType, null);
	}

	/**
	 * 根据主键类型生成序列，默认带6位日期前缀YYMMDD
	 * 格式：类型前缀 + 日期前缀+自增序列
	 * @param bizType
	 * @param userId
	 * @return
	 */
	public static Long getID(Integer bizType){
		return getID(bizType,null,true);
	}
	
	/**
	 * 根据主键类型生成序列
	 * 格式：类型前缀 + 日期前缀+自增序列
	 * @param datePrefix 是否带6位日期前缀YYMMDD
	 * @param bizType 主键类型
	 * @return
	 */
	public static Long getID(Integer bizType,boolean datePrefix){
		return getID(bizType,null,datePrefix);
	}

	/**
	 * 根据主键类型生成序列，默认带6位日期前缀YYMMDD
	 * 格式：类型前缀 + 日期前缀+自增序列+分表标识
	 * 分表标识：取用户id后两位，如果为纯数字直接使用，如果非纯数字取hash值
	 * @param bizType
	 * @param userId
	 * @return
	 */
	public static Long getID(Integer bizType,String userId){
		return getID(bizType,userId,true);
	}
	
	/**
	 * 根据主键类型生成序列
	 * 格式：类型前缀2+日期前缀6+自增序列9+分表标识2    =19位
	 * 分表标识：取用户id后两位，如果为纯数字直接使用，如果非纯数字取hash值
	 * @param bizType
	 * @param userId
	 * @param datePrefix ， 是否需要6位日期作为序列前缀
	 * @return
	 */
	public static Long getID(Integer bizType,String userId,boolean datePrefix){
	
	Long id = getID_(bizType,userId);
	
	if(id != null && id>0){
		if(datePrefix){
			return Long.parseLong(""+bizType+Primary.getYYMMDD() + id);
		}else{
			return Long.parseLong(""+bizType + id);
		}
	}
	
	return null;
    }


	private static Long getID_(Integer bizType,String userId){
	try{
	Long id = getID_(bizType);
	
//	String tabFlag = "";
		
//	if(userId != null && userId.trim().length()>2){
//		
//		tabFlag = userId.substring(userId.length()-2, userId.length());
//
//		if(!NumberUtils.isDigits(tabFlag)){
//			String tabs = String.valueOf(FNVHash1(tabFlag));
//			tabFlag = tabs.substring(tabs.length()-2, tabs.length());
//		}
//	}
	
	return id;
	
	}catch(Exception e){
		log.debug("IDMaker.getID Exception,bizType="+bizType+",userId="+userId,e);
	}
	return null;
}
	
	private static long getID_(Integer bizType){
	
		Long primaryKey = 0L;
		
		synchronized(bizType){
		Primary primary = container.get(bizType);

		try {
			
			if(primary == null){
				primary = new Primary(bizType);
				container.put(bizType, primary);
			}
			
			log.debug(primary);
			
			if(primary.getSeqKeeper().get()>0){
				primaryKey = primary.decrementAndGet();
				
			//当前容器用完后，直接切到下一个容器
			}else if(primary.getNextSeq().get()>0){
				primaryKey = primary.nextAndGet();
				log.debug("next:"+primary);
			}
			
			//是否需要预备装载下一个容器，以防在切换过程遇到并发问题。 需要更高的性能还可以异步更新
			if(primary.isSwitching()){
				primary.loadNextSeqKeeper();
			}
			
			
		} catch (Exception e) {
			log.error("IDMaker.getID SQLException", e);
		}
		
		//log.info(primaryKey);
		 return primaryKey;
		}
	}
	
	private static long FNVHash1(String str){
        final long p = 16777619;
        long hash = (int)2166136261L;
        for(int i=0;i<str.length();i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }
}
