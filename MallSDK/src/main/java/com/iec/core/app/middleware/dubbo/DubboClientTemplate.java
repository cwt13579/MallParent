package com.iec.core.app.middleware.dubbo;

import com.iec.core.app.middleware.IClient;
import com.iec.core.app.middleware.dubbo.service.IService;
import com.iec.core.app.middleware.dubbo.client.DubboClientAdapter;
import com.iec.core.app.middleware.dubbo.client.DubboDirectClient;

/**
 * @author tumq
 * @Date 2015年1月9日
 * @describe dubbo客户端发送请求模板
 */
public class DubboClientTemplate {
	private final static DubboClientFactory clientFactory = new DubboClientFactory();
	private final static DubboClientAdapter divAdapterClient = clientFactory.createAdapterClient();
	private final static DubboDirectClient directClient = clientFactory.createDirectClient(null);

	public static IClient getClient() {
		return divAdapterClient;
	}
	
	public static IClient getClient(IService service) {
		directClient.setService(service);
		return directClient;
	}
}
