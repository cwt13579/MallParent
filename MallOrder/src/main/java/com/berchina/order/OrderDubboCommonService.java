package com.berchina.order;


import com.alibaba.dubbo.config.annotation.Service;
import com.berchina.distribute.frame.base.IDubboCommonService;
import com.berchina.distribute.frame.base.impl.DubboCommonService;

/**
 * 
 * @ClassName:     OrderDubboCommonService.java
 * @Description:   Order服务接口实现
 * 
 * @author          cwt
 * @version         V1.0  
 * @Date           2017年5月5日 上午10:59:29
 */
@Service(group = "iDubboCommonService_order", version = "1.0", timeout = 5000)
public class OrderDubboCommonService  extends DubboCommonService implements IDubboCommonService {

	@Override
	public Object call(String serviceName, String methodName, Object... params) throws Exception {
		return super.doCall(serviceName, methodName, params);
	}

}
