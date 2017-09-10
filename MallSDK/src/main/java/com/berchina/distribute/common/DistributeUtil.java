package com.berchina.distribute.common;


/**
 * 
 * @ClassName:     DistributeUtil.java
 * @Description:   分布式工具
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:10:58
 */
public class DistributeUtil {

	private DistributeUtil(){
		
	}
	/**
	 * 根据服务类和服务标识生成服务名称
	 * @param clz 服务类
	 * @param tag 服务标识
	 * @return
	 */
	public static final String buildServiceName(Class<?> clz,Object tag){
		return clz.getName()+"@"+(tag==null?"":tag.toString());
	}
}
