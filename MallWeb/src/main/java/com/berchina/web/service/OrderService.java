
package com.berchina.web.service; 

import org.springframework.stereotype.Service;

import com.berchina.distribute.frame.client.ServiceManager;
import com.berchina.enums.OrderServiceType;
import com.berchina.service.order.IOrderService;
import com.berchina.vo.HashMapExt;
import com.berchina.web.command.CommandOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月12日 上午10:26:50 

 * 类说明 

 */
@Service
public class OrderService {
	@HystrixCommand(fallbackMethod = "addServiceFallback") 
	public String submitOrder() throws Exception {
		String result = ServiceManager.aquire(IOrderService.class, OrderServiceType.standard).submitOrder();
		return result;
	}
	@HystrixCommand(fallbackMethod = "addServiceFallback")  
	public String submitOrders(HashMapExt hmExt)throws Exception {
		
		CommandOrderService commandOrderSerice = new CommandOrderService(hmExt);
		return commandOrderSerice.execute();
	}
    public String addServiceFallback() {  
        return "error";  
    }  
}
 