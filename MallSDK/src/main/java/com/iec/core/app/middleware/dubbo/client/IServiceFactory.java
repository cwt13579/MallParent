package com.iec.core.app.middleware.dubbo.client;

import com.iec.core.app.middleware.dubbo.service.IService;

/**
 * Service工厂类接口
 * 
 * @author SCRCU
 * @version 1.0.0
 */
public interface IServiceFactory {
	
	/**
	 *获取远程服务对象
	 *@param	svcId 远程服务对象id
	 *@return	远程服务对象
	 */
	public IService getService(String svcId);

}
