package com.berchina.web.command; 

import java.util.List;

import com.berchina.distribute.frame.client.ServiceManager;
import com.berchina.enums.OrderServiceType;
import com.berchina.service.order.IOrderService;
import com.berchina.vo.HashMapExt;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月12日 上午11:40:46 

 * 类说明 

 */

public class CommandOrderService extends HystrixCommand<String>{
    private HashMapExt hmExt;
	public CommandOrderService(HashMapExt hmExt) {

		super(Setter
			    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
			    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
			    		.withExecutionIsolationThreadTimeoutInMilliseconds(2000)
			    		//.withCircuitBreakerSleepWindowInMilliseconds(30000)//熔断器中断请求30秒后会进入半打开状态,放部分流量过去重试
                        //.withCircuitBreakerErrorThresholdPercentage(50)//错误率达到50开启熔断保护
                        //.withCircuitBreakerRequestVolumeThreshold(20)//10秒钟内至少19此请求失败，熔断器才发挥起作用
                        //.withExecutionTimeoutEnabled(false)//使用dubbo的超时，禁用这里的超时
			    		)
			    		
			    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(60))
               
			    );  
		this.hmExt = hmExt;
	}

	@Override
	protected String run() throws Exception {
		List<String> result = ServiceManager.aquire(IOrderService.class, OrderServiceType.standard).submitOrders(this.hmExt);
		return result.toString();
	}
	
	 @Override  
	 protected String getFallback() {  
	        return "exeucute Falled";  
	} 
}
 