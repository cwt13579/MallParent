package com.iec.core.app.middleware.dubbo;

import com.iec.core.app.base.BeanContext;
import com.iec.core.app.middleware.dubbo.client.DubboServiceInvoker;
import com.iec.core.app.middleware.dubbo.client.IServiceInvoker;
import com.iec.core.app.middleware.dubbo.client.ServiceFactoryImpl;
import com.iec.core.app.middleware.dubbo.service.IService;
import com.iec.core.app.middleware.dubbo.client.DubboClientAdapter;
import com.iec.core.app.middleware.dubbo.client.DubboDirectClient;


/**
 * @author tump
 * @Date 2015年1月8日
 * @describe dubbo客户端工厂
 */
public class DubboClientFactory {
		
	/**
	 * @param clientConversion 
	 * @return 
	 * 创建适配器客户端
	 */
	public <R,Q> DubboClientAdapter createAdapterClient(){
		DubboServiceInvoker invoker = new DubboServiceInvoker();
		ServiceFactoryImpl impl = new ServiceFactoryImpl();
		impl.setApplicationContext(BeanContext.getApplicationContext());
		invoker.setServiceFactory(impl);
		return createAdapterClient(invoker);
	}
	
	public <R,Q> DubboClientAdapter createAdapterClient(IServiceInvoker serviceInvoker){
		DubboClientAdapter client = new DubboClientAdapter();
		client.setiServiceInvoker(serviceInvoker);
		return client;
	}
	
	public <R,Q> DubboDirectClient createDirectClient(IService service){
		DubboDirectClient client = new DubboDirectClient();
		client.setService(service);
		return client;
	}
}
