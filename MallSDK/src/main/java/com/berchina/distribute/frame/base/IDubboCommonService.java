package com.berchina.distribute.frame.base;

/**
 * 
 * @ClassName:     IDubboCommonService.java
 * @Description:   通用dubbo服务 
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:15:57
 */
public interface IDubboCommonService {
	
	/**
	 * dubbo调用
	 * @param serviceName 服务名称
	 * @param methodName 方法名称
	 * @param args 参数
	 * @return 服务返回值
	 * @throws Exception
	 */
	Object call(String serviceName,String methodName,Object... args)throws Exception;
}
