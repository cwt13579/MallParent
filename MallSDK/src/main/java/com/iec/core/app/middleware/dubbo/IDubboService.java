package com.iec.core.app.middleware.dubbo;

import org.springframework.context.ApplicationContext;

import com.iec.core.app.middleware.dubbo.service.IService;

/**
 * @author tump
 * @Date 2015年1月8日
 * @describe dubbo服务端接口
 */
public interface IDubboService extends IService{
	public void init(ApplicationContext applicationContext) throws Exception;
}
