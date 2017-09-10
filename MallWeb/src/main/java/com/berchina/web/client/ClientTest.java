
package com.berchina.web.client; 

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.dubbo.container.page.Page;
import com.berchina.vo.HashMapExt;
import com.berchina.web.service.OrderService;
import com.cm.common.mapping.UserMapping;
import com.google.common.reflect.TypeToken;
import com.iec.core.app.middleware.dubbo.DubboClientTemplate;
import com.iec.core.app.middleware.dubbo.ServiceMethodRequest;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月5日 下午5:28:55 

 * 类说明 

 */

public class ClientTest {

	public static void main(String args[]) throws Exception {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring-conf/applicationContext.xml");
        HashMapExt hmExt = new HashMapExt();
		System.out.println(hmExt.getClass());
//		String result = ServiceManager.aquire(IOrderService.class, OrderServiceType.standard).submitOrder();
//		System.out.println(result);
//		ServiceManager.aquire(IOrderService.class, OrderServiceType.standard).submitOrders();
		OrderService orderService = context.getBean(OrderService.class);
		String result = orderService.submitOrder();
		System.out.println("result:"+result);

		hmExt.put("name", "cwt");
		hmExt.put("value", 200);
		
		hmExt.setPageIndex(1);
		hmExt.setPageSize(10);
		
		for(int i = 0 ; i < 1; i++) {
			result = orderService.submitOrders(hmExt);
			System.out.println("submitOrders result:"+result);
		}
		
//        ServiceMethodRequest request=new ServiceMethodRequest();
//        request.setServiceId(UserMapping.USER_FACADE);
//        request.setMethodId(UserMapping.USER_LIST);
//        DubboClientTemplate.getClient().send(request);
		
	}
}
 